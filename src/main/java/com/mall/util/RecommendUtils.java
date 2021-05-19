package com.mall.util;

import com.mall.dataobject.Product;
import com.mall.dataobject.UserActiveDTO;
import com.mall.dataobject.UserSimilarityDTO;
import com.mall.service.Impl.ActiveServiceImpl;
import com.mall.service.Impl.SimilarityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class  RecommendUtils {

    private ClassPathXmlApplicationContext application;

    public static boolean updateBuyingBehavior(Integer userId, Integer productId) {
        boolean flag = false;
        //Todo
        return flag;
    }

    public static ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Long>> assembleUserBehavior(List<UserActiveDTO> userActiveList) {
        ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Long>> activeMap = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Long>>();
        for(UserActiveDTO userActive : userActiveList) {
            Integer userId = userActive.getUserId();
            Integer category2Id = userActive.getCategory2Id();
            Long hits = userActive.getHits();
            if(activeMap.containsKey(userId)) {
                ConcurrentHashMap<Integer, Long> tempMap = activeMap.get(userId);
                tempMap.put(category2Id, hits);
                activeMap.put(userId, tempMap);
            } else {
                ConcurrentHashMap<Integer, Long> category2Map = new ConcurrentHashMap<Integer, Long>();
                category2Map.put(category2Id, hits);
                activeMap.put(userId, category2Map);
            }
        }
        return activeMap;
    }

    /**
     * 计算用户与用户之间的相似性，返回计算出的用户与用户之间的相似度对象
     * @param activeMap 用户对各个二级类目的购买行为的一个map集合
     * @return 计算出的用户与用户之间的相似度的对象存储形式
     */
    public static List<UserSimilarityDTO> calcSimilarityBetweenUsers(ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Long>> activeMap) {
        List<UserSimilarityDTO> similarityList = new ArrayList<UserSimilarityDTO>();
        Set<Integer> userSet = activeMap.keySet();
        List<Integer> userIdList = new ArrayList<Integer>(userSet);
        if(userIdList.size() < 2) return similarityList;

        for(int i=0;i<userIdList.size() -1;i++) {
            for(int j=i+1;j<userIdList.size();j++){
                //获取两个用户对每个二级类目的点击量
                ConcurrentHashMap<Integer, Long> userCategory2Map = activeMap.get(userIdList.get(i));
                ConcurrentHashMap<Integer, Long> userRefCategory2Map = activeMap.get(userIdList.get(j));

                // 获取两个map种二级类目id的集合
                Set<Integer> key1Set = userCategory2Map.keySet();
                Set<Integer> key2Set = userRefCategory2Map.keySet();
                Iterator<Integer> it1 = key1Set.iterator();
                Iterator<Integer> it2 = key2Set.iterator();

                // 两用户之间的相似度
                double similarity = 0.0;
                // 余弦相似度公式中的分子
                double molecule = 0.0;
                // 余弦相似度公式中的分母
                double denominator = 1.0;
                // 余弦相似度公式中分母根号下的两个向量的模的值
                double vector1 = 0.0;
                double vector2 = 0.0;

                while (it1.hasNext() && it2.hasNext()) {
                    Integer it1Id = it1.next();
                    Integer it2Id = it2.next();
                    // 获取二级类目对应的点击次数
                    Long hits1 = userCategory2Map.get(it1Id);
                    Long hits2 = userRefCategory2Map.get(it2Id);
                    // 累加分子
                    molecule += hits1 * hits2;
                    // 累加分母中的两个向量的模
                    vector1 += Math.pow(hits1, 2);
                    vector2 += Math.pow(hits2, 2);
                }
                // 计算分母
                denominator = Math.sqrt(vector1) * Math.sqrt(vector2);
                // 计算整体相似度
                similarity = molecule / denominator;

                UserSimilarityDTO userSimilarityDTO = new UserSimilarityDTO();
                userSimilarityDTO.setUserId(userIdList.get(i));
                userSimilarityDTO.setUserReId(userIdList.get(j));
                userSimilarityDTO.setSimilarity(similarity);
                similarityList.add(userSimilarityDTO);
            }
        }
        return similarityList;
    }

    /**
     * 找出与userId购买行为最相似的topN个用户
     * @param userId 需要参考的用户id
     * @param userSimilarityDTOList 用户相似度列表
     * @param topN 与userId相似用户的数量
     * @return 与usereId最相似的topN个用户
     */
    public static List<Integer> getSimilarityBetweenUsers(Integer userId, List<UserSimilarityDTO> userSimilarityDTOList, Integer topN) {
        List<Integer> similarityList = new ArrayList<Integer>(topN);
        PriorityQueue<UserSimilarityDTO> minHeap = new PriorityQueue<UserSimilarityDTO>(new Comparator<UserSimilarityDTO>() {
            @Override
            public int compare(UserSimilarityDTO o1, UserSimilarityDTO o2) {
                if (o1.getSimilarity() - o2.getSimilarity() > 0) {
                    return 1;
                } else if (o1.getSimilarity() - o2.getSimilarity() == 0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        for(UserSimilarityDTO userSimilarityDTO : userSimilarityDTOList) {
            if(minHeap.size() < topN) {
                minHeap.offer(userSimilarityDTO);
                System.out.println(minHeap.peek().getSimilarity());
            } else if (minHeap.peek().getSimilarity() < userSimilarityDTO.getSimilarity()) {
                minHeap.poll();
                minHeap.offer(userSimilarityDTO);
            }
        }

        for (UserSimilarityDTO userSimilarityDTO : minHeap) {
            similarityList.add(userSimilarityDTO.getUserId().equals(userId) ? userSimilarityDTO.getUserReId() : userSimilarityDTO.getUserId());
        }

        return similarityList;
    }

    /**
     * 到similarUserList中的用户访问的二级类目中查找userId不经常点击的二级类目中获得被推荐的类目id
     * @param userId 被推荐商品的用户id
     * @param similarUserList 用userId相似的用户集合
     * @param userActiveList 所有用户的浏览行为
     * @return 可以推荐给userId的二级类目id列表
     */
    public static List<Integer> getRecommendateCategory2(Integer userId, List<Integer> similarUserList, List<UserActiveDTO> userActiveList) {
        List<Integer> recommendateProductList = new ArrayList<Integer>();

        List<UserActiveDTO> userIdActiveList = findUsersBrowsBehavior(userId, userActiveList);

        // 对userId的浏览行为按照二级类目id排个序，方便后续与推荐的用户的浏览行为中的二级类目的点击次数直接相减，避免时间复杂度为O(n2)
        Collections.sort(userIdActiveList, new Comparator<UserActiveDTO>(){
            @Override
            public int compare(UserActiveDTO o1, UserActiveDTO o2) {
                return o1.getCategory2Id().compareTo(o2.getCategory2Id());
            }
        });

        for (Integer refId : similarUserList) {
            // 计算当前用户所点击的二级类目次数与被推荐的用户所点击的二级类目的次数的差值
            // 找到当前这个用户的浏览行为
            List<UserActiveDTO> currActiveList = findUsersBrowsBehavior(refId, userActiveList);

            // 排序，同上述理由
            Collections.sort(currActiveList, new Comparator<UserActiveDTO>(){
                @Override
                public int compare(UserActiveDTO o1, UserActiveDTO o2) {
                    return o1.getCategory2Id().compareTo(o2.getCategory2Id());
                }
            });

            // 记录差值最大的二级类目的id
           Integer maxCategory2 = 0;

            // 记录最大的差值
            double maxDifference = 0.0;
            for (int i = 0; i < currActiveList.size(); i++) {
                // 求出点击量差值最大的二级类目，即为要推荐的类目
                double difference = Math.abs(currActiveList.get(i).getHits() - userIdActiveList.get(i).getHits());
                if (difference > maxDifference) {
                    maxDifference = difference;
                    maxCategory2 = currActiveList.get(i).getCategory2Id();
                }
            }
            recommendateProductList.add(maxCategory2);
        }
        return recommendateProductList;

    }

    /**
     * 找到当前用户的浏览行为列表
     * @param userId 当前用户id
     * @param userActiveList 所有用户的浏览行为列表
     * @return 当前用户的浏览行为列表
     */
    public static List<UserActiveDTO> findUsersBrowsBehavior(Integer userId, List<UserActiveDTO> userActiveList) {
        List<UserActiveDTO> currActiveList = new ArrayList<UserActiveDTO>();
        for (UserActiveDTO userActiveDTO : userActiveList) {
            if (userActiveDTO.getUserId().equals(userId)) {
                currActiveList.add(userActiveDTO);
            }
        }
        return currActiveList;
    }

    /**
     * 找到当前商品列表中点击量最高的商品
     * @param productList 商品列表
     * @return 点击量最高的商品
     */
    public static Product findMaxHitsProduct(List<? extends Product> productList) {
        if (productList == null || productList.size() == 0) {
            return null;
        }
        // 记录当前最大的点击量
        Random random = new Random();
        int ran = random.nextInt(4);
        // 记录当前点击量最大的商品

        return productList.get(ran);
    }
}
