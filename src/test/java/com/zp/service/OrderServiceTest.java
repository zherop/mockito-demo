package com.zp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.zp.model.GiftCard;
import com.zp.model.Goods;

/**
 * @author zp
 * @date 2019年3月5日
 */

public class OrderServiceTest {

	@InjectMocks
	private OrderService orderService = new OrderService();

	@Mock
	private GiftCardService giftCardService;

	@Mock
	private RechargeAccountService rechargeAccountService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * 使用赠卡，余额
	 */
	@Test
	public void payment() {
		String userName = "zp";
		double rechargeAccount = 20.0;

		List<Goods> purchaseGoods = new ArrayList<>();
		purchaseGoods.add(new Goods("Java编程思想", 50.0, 1, true));
		purchaseGoods.add(new Goods("Spring Cloud实战", 20.0, 1, true));
		purchaseGoods.add(new Goods("图解机器学习", 60.0, 2, false));

		List<GiftCard> giftCards = new ArrayList<>();
		giftCards.add(new GiftCard(UUID.randomUUID().toString(), 50.0));
		giftCards.add(new GiftCard(UUID.randomUUID().toString(), 100.0));
		giftCards.add(new GiftCard(UUID.randomUUID().toString(), 200.0));

		Mockito.when(giftCardService.list(userName)).thenReturn(giftCards);
		Mockito.when(rechargeAccountService.getBalance(userName)).thenReturn(rechargeAccount);

		double payment = orderService.payment(userName, purchaseGoods);
		Assert.assertTrue(payment == 100.0);
		System.out.println("\n");
	}

	/**
	 * 只使用赠卡，不使用余额
	 */
	@Test
	public void payment1() {
		String userName = "zp";
		double rechargeAccount = 0.0;

		List<Goods> purchaseGoods = new ArrayList<>();
		purchaseGoods.add(new Goods("Java编程思想", 60.0, 1, true));

		List<GiftCard> giftCards = new ArrayList<>();
		giftCards.add(new GiftCard(UUID.randomUUID().toString(), 50.0));
		giftCards.add(new GiftCard(UUID.randomUUID().toString(), 100.0));
		giftCards.add(new GiftCard(UUID.randomUUID().toString(), 200.0));

		Mockito.when(giftCardService.list(userName)).thenReturn(giftCards);
		Mockito.when(rechargeAccountService.getBalance(userName)).thenReturn(rechargeAccount);

		double payment = orderService.payment(userName, purchaseGoods);
		Assert.assertTrue(payment == 0.0);
		System.out.println("\n");
	}

	/**
	 * 不使用赠卡（没有赠卡），没有账户余额
	 */
	@Test
	public void payment2() {
		String userName = "zp";
		double rechargeAccount = 0.0;

		List<Goods> purchaseGoods = new ArrayList<>();
		purchaseGoods.add(new Goods("Java编程思想", 60.0, 1, true));

		List<GiftCard> giftCards = new ArrayList<>();

		Mockito.when(giftCardService.list(userName)).thenReturn(giftCards);
		Mockito.when(rechargeAccountService.getBalance(userName)).thenReturn(rechargeAccount);

		double payment = orderService.payment(userName, purchaseGoods);
		Assert.assertTrue(payment == 60.0);
		System.out.println("\n");
	}

	/**
	 * 不使用赠卡（有赠卡），没有账户余额
	 */
	@Test
	public void payment3() {
		String userName = "zp";
		double rechargeAccount = 0.0;

		List<Goods> purchaseGoods = new ArrayList<>();
		purchaseGoods.add(new Goods("Java编程思想", 60.0, 1, false));

		List<GiftCard> giftCards = new ArrayList<>();
		giftCards.add(new GiftCard(UUID.randomUUID().toString(), 50.0));

		Mockito.when(giftCardService.list(userName)).thenReturn(giftCards);
		Mockito.when(rechargeAccountService.getBalance(userName)).thenReturn(rechargeAccount);

		double payment = orderService.payment(userName, purchaseGoods);
		Assert.assertTrue(payment == 60.0);
		System.out.println("\n");
	}

	/**
	 * 没有赠卡，有账户余额
	 */
	@Test
	public void payment4() {
		String userName = "zp";
		double rechargeAccount = 10.0;

		List<Goods> purchaseGoods = new ArrayList<>();
		purchaseGoods.add(new Goods("Java编程思想", 60.0, 1, true));

		List<GiftCard> giftCards = new ArrayList<>();

		Mockito.when(giftCardService.list(userName)).thenReturn(giftCards);
		Mockito.when(rechargeAccountService.getBalance(userName)).thenReturn(rechargeAccount);

		double payment = orderService.payment(userName, purchaseGoods);
		Assert.assertTrue(payment == 50.0);
	}
}
