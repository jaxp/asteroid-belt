package com.pallas.common.converter;

import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/8/24 13:41
 * @desc:
 */
@NoArgsConstructor
public class BaseConverter<A, B> {
  private Type[] typeArr;

  public BaseConverter(Type a, Type b) {
    typeArr = new Class[2];
    typeArr[0] = a;
    typeArr[1] = b;
  }

  public B forward(A a) {
    B b = newB();
    return forward(a, b);
  }

  public B forward(A a, B b) {
    if (Objects.isNull(a)) {
      return null;
    }
    if (Objects.isNull(b)) {
      b = newB();
    }
    return doForward(a, b);
  }

  public B doForward(A a, B b) {
    BeanUtils.copyProperties(a, b);
    return b;
  }

  public A reverse(B b) {
    A a = newA();
    return reverse(b, a);
  }

  public A reverse(B b, A a) {
    if (Objects.isNull(b)) {
      return null;
    }
    if (Objects.isNull(a)) {
      a = newA();
    }
    return doReverse(b, a);
  }

  public A doReverse(B b, A a) {
    BeanUtils.copyProperties(b, a);
    return a;
  }

  public List<B> forward(List<A> a) {
    return a.stream().map(e -> forward(e)).collect(Collectors.toList());
  }

  public List<A> reverse(List<B> b) {
    return b.stream().map(e -> reverse(e)).collect(Collectors.toList());
  }

  public A newA() {
    return (A) newObj(0);
  }

  public B newB() {
    return (B) newObj(1);
  }

  private Object newObj(int i) {
    if (Objects.isNull(typeArr)) {
      typeArr = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
    }

    Object obj = null;
    try {
      obj = ((Class)typeArr[i]).newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return obj;
  }
}
