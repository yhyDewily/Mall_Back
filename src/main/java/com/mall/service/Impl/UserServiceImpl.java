package com.mall.service.Impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.common.TokenCache;
import com.mall.dataobject.Product;
import com.mall.dataobject.User;
import com.mall.dataobject.UserActiveDTO;
import com.mall.dataobject.UserSimilarityDTO;
import com.mall.repository.ProductRepository;
import com.mall.repository.UserActiveRepository;
import com.mall.repository.UserRepository;
import com.mall.repository.UserSimilarityRepository;
import com.mall.service.UserService;
import com.mall.util.MD5Util;
import com.mall.util.RecommendUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserSimilarityRepository userSimilarityRepository;

    @Autowired
    private UserActiveRepository activeRepository;

    @Autowired
    private ProductRepository productRepository;

    private static Map<String, String> Identify;

    private static Map<String, Date> checkTime;

    static {
        Identify = new HashMap<String, String>();
        checkTime = new HashMap<String, Date>();
    }

    @Override
    public ServerResponse checkAnswer(Integer userId, String question, String answer) {
        int resultCount = repository.checkAnswer(userId, question, answer);
        if (resultCount > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("问题答案错误");
    }

    @Override
    public int updateAll(User user) {
        User saveUser = repository.findByUserId(user.getId());
        saveUser.setUsername(user.getUsername());
        saveUser.setSex(user.getSex());
        saveUser.setEmail(user.getEmail());
        saveUser.setPhone(user.getPhone());
        saveUser.setQuestion(user.getQuestion());
        saveUser.setAnswer(user.getAnswer());
        try {
            repository.save(saveUser);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    @Override
    public ServerResponse<User> update_information(User user) {
        int resultCount = repository.checkEmailByUserId(user.getEmail(), user.getId());
        int userId = repository.checkEmail(user.getEmail());
        if (resultCount > 0 && userId!=user.getId()) return ServerResponse.createByErrorMessage("email已存在");
        int updateCount = this.updateAll(user);
        if (updateCount > 0) return ServerResponse.createBySuccess("更新个人信息成功", user);
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }


    @Override
    public ServerResponse<User> getInformation(Integer id) {
        User user = repository.findByUserId(id);
        if (user == null) return ServerResponse.createByErrorMessage("找不到当前用户");
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = repository.checkUsername(username);
        if (resultCount == 0) return ServerResponse.createByErrorMessage("用户名不存在");

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = repository.findByUsernameAndPassword(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    @Override
    public User getUserInfo(Integer userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        try {
            repository.save(user);
            for(int i=100003;i<100019;i++) {
                UserActiveDTO userActiveDTO = new UserActiveDTO();
                userActiveDTO.setUserId(user.getId());
                userActiveDTO.setCategory2Id(i);
                userActiveDTO.setHits(0L);
                activeRepository.save(userActiveDTO);
            }
            //计算用户相似度
            //1.查询所有用户浏览记录
            List<UserActiveDTO> userActiveDTOList = activeRepository.findAll();
            //2.存储二级类目点击量
            ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Long>> activeMap = RecommendUtils.assembleUserBehavior(userActiveDTOList);
            // 3.调用推荐模块工具类的方法计算用户与用户之间的相似度
            List<UserSimilarityDTO> similarityList = RecommendUtils.calcSimilarityBetweenUsers(activeMap);
            for(UserSimilarityDTO usim : similarityList) {
                //先判断是否存在两个用户之间的相似度计算
                if(userSimilarityRepository.countUserSimilarity(usim.getUserId(), usim.getUserReId())>0) {
                    UserSimilarityDTO similarityDTO = userSimilarityRepository.findByUserIdAndUserReId(usim.getUserId(), usim.getUserReId());
                    try {
                        similarityDTO.setSimilarity(usim.getSimilarity());
                        userSimilarityRepository.save(similarityDTO);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    try {
                        userSimilarityRepository.save(usim);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }

        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                int resultCount = repository.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resultCount = repository.checkEmail(str);
                if (resultCount > 0) return ServerResponse.createByErrorMessage("email已存在");
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(Integer userId) {
        String question = repository.findQuestionByUserId(userId);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题不存在");
    }

    @Override
    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) return ServerResponse.createByErrorMessage("参数错误,需要传递Token");
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) return ServerResponse.createByErrorMessage("用户不存在");

        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) return ServerResponse.createByErrorMessage("token无效或过期");

        if (StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = repository.updatePasswordByUsername(username, md5Password);

            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        } else return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<String> restPassword(Integer userId, String passwordNew) {
        User user = repository.findByUserId(userId);
        String Md5pwd = MD5Util.MD5EncodeUtf8(passwordNew);
        user.setPassword(Md5pwd);
        try {
            repository.save(user);
            return ServerResponse.createBySuccess("密码修改成功");
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("密码修改失败");
        }
    }

    @Override
    public ServerResponse<Object> checkAdminRole(User user) {
        if (user != null && user.getRole() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    public int checkPhone(String mobile) {
        return repository.checkPhone(mobile);
    }

    public int getUserIdByPhone(String mobile) {
        return repository.findUserByPhone(mobile).getId();
    }

    public ServerResponse getUserList(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<User> users = repository.findAll(pageable);
        return ServerResponse.createBySuccess(users);
    }

    public ServerResponse setAdmin(Integer anotherUser) {
        User user = repository.findByUserId(anotherUser);
        if(user != null) {
            user.setRole(1);
            repository.save(user);
            return ServerResponse.createBySuccessMessage("设置成功");
        }
        return ServerResponse.createByErrorMessage("设置失败");
    }

    public int checkSimilarity(Integer userId) {
        return userSimilarityRepository.countUserSimilarity(userId);
    }

    @Override
    public List<Product> getSimilarity(Integer userId) {
        // 2.找到当前用户与其他用户的相似度列表
        List<UserSimilarityDTO> userSimilarityList = userSimilarityRepository.findAllByUserIdOrUserReId(userId, userId);
        // 3.找到与当前用户相似度最高的topN个用户
        Integer topN = 3;
        List<Integer> userIds = RecommendUtils.getSimilarityBetweenUsers(userId, userSimilarityList, topN);
        // 4.从这N个用户中先找到应该推荐给用户的二级类目的id
        List<UserActiveDTO> userActiveList = activeRepository.findAll();
        List<Integer> category2List = RecommendUtils.getRecommendateCategory2(userId, userIds, userActiveList);
        //5.找到这些二级类目下点击量最大的商品
        List<Product> recommendateProducts = new ArrayList<Product>();
        for(int i=0;i<category2List.size();i++) {
            List<Product> productList = productRepository.findByHitsHotAndCategory(category2List.get(i));
            Random random = new Random();
            Product product = productList.get(random.nextInt(4));
            if(!recommendateProducts.contains(product)) {
                recommendateProducts.add(product);
            } else i--;
        }
        return recommendateProducts;
    }

    public ServerResponse sendSms(String mobile) {
        //生成6位数验证码
        String vcode = "";
        for(int i=0;i<6;i++) {
            vcode = vcode + (int)(Math.random()*9);
        }
        //将手机号和对应验证码放入Map
        Identify.put(mobile, vcode);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GDhc7Z4bSZDevtpd7V6", "UEcWaql7VZItlPDSH99QIQdm3y0xDl");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //设置验证码过期时间
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = new java.util.Date();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 5);
        checkTime.put(mobile, calendar.getTime());
//        return ServerResponse.createBySuccess(vcode);
        //设置短信发送参数
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "品牌服饰空中商城");
        request.putQueryParameter("TemplateCode", "SMS_190786589");
        request.putQueryParameter("TemplateParam", "{ \"code\":\""+vcode+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return ServerResponse.createBySuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return ServerResponse.createByErrorMessage("短信发送失败");
    }

    public ServerResponse checkIdentify(String mobile, String vcode) {
        if(!Identify.containsKey(mobile)) return ServerResponse.createByErrorMessage("手机号错误");
        String rightVcode = Identify.get(mobile);
        Date date = new Date();
        if (vcode.equals(rightVcode) && date.before(checkTime.get(mobile))) {
            Identify.remove(mobile, vcode);
            return ServerResponse.createBySuccess("验证成功");
        }
        if(date.after(checkTime.get(mobile))) return ServerResponse.createByErrorMessage("已超过验证时间");
        return ServerResponse.createByError();
    }
}
