package com.liuxiaocs.sync;

import java.util.concurrent.TimeUnit;

class Phone {

    public synchronized void sendSMS() throws Exception {
        // 停留4秒
        TimeUnit.SECONDS.sleep(4);
        System.out.println("------sendSMS");
    }

    public synchronized void sendEmail() throws Exception {
        System.out.println("------sendEmail");
    }

    public void getHello() {
        System.out.println("------getHello");
    }

    public static synchronized void sendSMS1() throws Exception {
        // 停留4秒
        TimeUnit.SECONDS.sleep(4);
        System.out.println("------sendSMS");
    }

    public static synchronized void sendEmail1() throws Exception {
        System.out.println("------sendEmail");
    }
}

/**
 * @Description: 8锁
 *
 *
synchronized 作用在方法上锁的是对象

1 标准访问，先打印短信还是邮件
主线程中sleep(100)
------sendSMS
------sendEmail

2 停4秒在短信方法内，先打印短信还是邮件
因为sendSMS先拿到了对象锁
------sendSMS
------sendEmail

3 新增普通的hello方法，是先打短信还是hello
hello()不需要获取锁，4秒之后才打印短信
------getHello
------sendSMS

4 现在有两部手机，先打印短信还是邮件
先打印Email，因为是两个不同的对象锁，并且SMS中休眠了4秒
------sendEmail
------sendSMS

5 两个静态同步方法，1部手机，先打印短信还是邮件
先SMS后Email，使用的是同一个类锁
------sendSMS
------sendEmail

6 两个静态同步方法，2部手机，先打印短信还是邮件
先SMS后Email，使用的是同一个类锁
------sendSMS
------sendEmail

7 1个静态同步方法,1个普通同步方法，1部手机，先打印短信还是邮件
静态同步方法使用的是类锁，普通同步方法使用的是对象锁
Email先输出，SMS要等4秒
------sendEmail
------sendSMS

8 1个静态同步方法,1个普通同步方法，2部手机，先打印短信还是邮件
静态同步方法使用的是类锁，普通同步方法使用的是对象锁
Email先输出，SMS要等4秒
------sendEmail
------sendSMS

 */
public class Lock_8 {
    public static void main(String[] args) throws Exception {

        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            try {
//                phone.sendSMS();

                phone.sendSMS1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "AA").start();

        // 这里加了一个sleep(), 保证结果是唯一的
        Thread.sleep(100);

        new Thread(() -> {
            try {
//                 phone.sendEmail();
//                 phone.getHello();
                phone2.sendEmail();

//                phone2.sendEmail1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BB").start();
    }
}
