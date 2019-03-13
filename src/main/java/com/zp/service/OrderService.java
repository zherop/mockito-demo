package com.zp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zp.model.GiftCard;
import com.zp.model.Goods;

/**
 * 订单服务类
 * 
 * @author zp
 * @date 2019年3月5日
 */
@Service
public class OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	/**
	 * 赠卡服务类
	 */
	@Autowired
	private GiftCardService giftCardService;

	/**
	 * 充值账户服务类
	 */
	@Autowired
	private RechargeAccountService rechargeAccountService;

	/**
	 * 1).用户具有充值账户，账户中有余额或余额为0
	 * 2).用户拥有赠卡：赠卡金额为50,100,200的一张或多张，每次购物只能用最多一张
	 * 3).购买的商品可设置为允许使用赠卡或不允许使用赠卡
	 * 4).购买过程中优先使用赠卡，其次使用用户充值账户余额，不足时需要支付现金
	 * 
	 * @param userName
	 * @param purchaseGoods 购买的商品
	 * @return 待支付金额
	 */
	public double payment(String userName, List<Goods> purchaseGoods) {
		Order order = calculateOrder(purchaseGoods);
		// 商品总额
		double totalAmount = order.totalAmount;
		// 支持赠卡的商品总额
		double supportGiftCardAmount = order.supportGiftCardAmount;
		logger.info("商品总额: {}, 支持赠卡的商品总额: {}", totalAmount, supportGiftCardAmount);

		// 待支付金额
		double waitForPayment = totalAmount;

		// 使用赠卡
		if (supportGiftCardAmount > 0) {
			double deductionAmount = deductAmountUseGiftCard(userName, supportGiftCardAmount);
			waitForPayment = waitForPayment - deductionAmount;
			logger.info("使用赠卡抵扣金额: {}", deductionAmount);
		}

		// 使用余额
		if (waitForPayment > 0) {
			double deductionAmount = deductAmountUseAccount(userName, waitForPayment);
			waitForPayment = waitForPayment - deductionAmount;
			logger.info("使用充值账号抵扣金额: {}", deductionAmount);
		}

		logger.info("还需支付金额: {}", waitForPayment);
		return waitForPayment;
	}

	/**
	 * 使用账号抵扣金额
	 * 
	 * @param userName
	 * @param maxDeductionAmount
	 * @return 账号可抵扣金额
	 */
	private double deductAmountUseAccount(String userName, double maxDeductionAmount) {
		double deductionAmount = 0;
		double accountBalance = rechargeAccountService.getBalance(userName);
		logger.info("充值账号余额: {}", accountBalance);
		if (accountBalance > 0) {
			deductionAmount = Math.min(accountBalance, maxDeductionAmount);
		}
		return deductionAmount;
	}

	/**
	 * 使用赠卡抵扣金额
	 * 
	 * @param userName
	 * @param maxDeductionAmount
	 * @return 赠卡可抵扣金额
	 */
	private double deductAmountUseGiftCard(String userName, double maxDeductionAmount) {
		double deductionAmount = 0.0;
		// 用户的赠卡
		List<GiftCard> giftCards = giftCardService.list(userName);

		// 小于等于 supportGiftCardAmount的最大赠卡
		GiftCard maxLeftGiftCard = null;

		// 大于 supportGiftCardAmount的最小赠卡
		GiftCard minRightGiftCard = null;
		for (GiftCard giftCard : giftCards) {
			if (giftCard.getAmount() <= maxDeductionAmount) {
				maxLeftGiftCard = giftCard;
			} else {
				minRightGiftCard = giftCard;
				break;
			}
		}
		// 优先使用赠卡全部抵扣
		GiftCard endUseGiftCard = minRightGiftCard == null ? maxLeftGiftCard : minRightGiftCard;
		if (endUseGiftCard != null) {
			deductionAmount = Math.min(maxDeductionAmount, endUseGiftCard.getAmount());
			logger.info("选择赠卡: {}", endUseGiftCard);
		}
		return deductionAmount;
	}

	private Order calculateOrder(List<Goods> purchaseGoods) {
		double totalAmount = 0.0;
		double supportGiftCardAmount = 0.0;
		for (Goods goods : purchaseGoods) {
			double singleGoodsAmount = goods.getPrice() * goods.getNum();
			if (goods.isSupportGiftCard()) {
				supportGiftCardAmount += singleGoodsAmount;
			}
			totalAmount += singleGoodsAmount;
		}
		Order order = new Order();
		order.totalAmount = totalAmount;
		order.supportGiftCardAmount = supportGiftCardAmount;
		return order;
	}

	class Order {
		double totalAmount;
		double supportGiftCardAmount;
	}
}
