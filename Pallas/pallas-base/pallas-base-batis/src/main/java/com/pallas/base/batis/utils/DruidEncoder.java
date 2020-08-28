package com.pallas.base.batis.utils;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * @author: jax
 * @time: 2020/8/24 11:29
 * @desc:
 */
public class DruidEncoder {

  public static void main(String[] args) throws Exception {
    String password = "password";
    String[] arr = ConfigTools.genKeyPair(512);
    System.out.println("privateKey:" + arr[0]);
    System.out.println("publicKey:" + arr[1]);
    System.out.println("password:" + ConfigTools.encrypt(arr[0], password));
  }

}
