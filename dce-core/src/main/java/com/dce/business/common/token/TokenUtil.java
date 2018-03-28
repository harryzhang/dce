package com.dce.business.common.token;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.dce.business.common.redis.JedisClient;
import com.dce.business.common.util.SpringBeanUtil;

public class TokenUtil {
	public static final String TS = "ts"; // 时间戳参数名
	public static final String USER_ID = "userId"; // 用户id参数名
	public static final String SIGN = "sign"; // 签名参数名
	public static final Long TOKEN_TIMEOUT_TIME = 30 * 60L; // token失效时间，单位秒
	private static final String TOKEN_PREFIX = "token#";

	public static String createToken(Integer userId) {
		String str = UUID.randomUUID().toString() + userId;
		String token = new String(Base64.encodeBase64(str.getBytes()));

		setToken(userId, token);
		return token;
	}

	public static boolean checkToken(String uri, Integer userId, String ts, String sign) {
		// TODO 先屏蔽测试
		// if (true)
		// return true;

		long currentTime = System.currentTimeMillis();
		long requestTime = Long.valueOf(ts);
		long diffTime = (currentTime - requestTime) / 1000;
		if (diffTime > TOKEN_TIMEOUT_TIME) {
			return false;
		}

		String checkSign = md5(userId, ts, uri);
		return checkSign.equals(sign);
	}

	/**
	 * 从缓存中获取用户令牌
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	private static String getToken(Integer userId) {
		JedisClient jedisClient = SpringBeanUtil.getBean("jedisClient");
		String token = jedisClient.getString(TOKEN_PREFIX + userId);

		return token;
	}

	/**
	 * 更新令牌
	 * 
	 * @param userId
	 *            用户id
	 * @param token
	 *            令牌
	 */
	private static void setToken(Integer userId, String token) {
		JedisClient jedisClient = SpringBeanUtil.getBean("jedisClient");
		jedisClient.setString(TOKEN_PREFIX + userId, token, -1);
	}

	private static String md5(Integer userId, String ts, String uri) {
		return DigestUtils.md5Hex(getToken(userId) + ts + uri);
	}
}
