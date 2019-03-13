package com.zp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zp.model.GiftCard;

/**
 * 赠卡服务类
 * 
 * @author zp
 * @date 2019年3月5日
 */
@Service
public class GiftCardService {

	/**
	 * 获取用户的赠卡
	 * 
	 * @param userName
	 * @return
	 */
	public List<GiftCard> list(String userName) {
		return new ArrayList<>();
	}

}
