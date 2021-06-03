--$09scala-demo--scala写的
1.chapter01 :scala语言的helloworld以及scala中main方法的讲解
2.chapter02:基本语法
    注释
    标识符命名
    变量定义:val/var
    字符串操作
    键盘输入
    数据类型
    基本转换:数字转字符串，字符串转数字，数字转数字
3.chapter03
    运算符
4.chapter04 流程控制
    块表达式
    if语句
    for循环
    while循环
    break，continue实现
5.函数式编程
    方法定义
        可变参数，带名参数，默认参数
        模拟读取hive数仓前7天的数据
    函数定义
    函数和方法的区别
    高阶函数
        练习高阶函数:
           1、对数组中的每个元素进行操作,操作规则由外部决定
            Array("spark","hello","java","hadoop")
            规则: 获取每个元素的长度 [不确定]
            结果: Array(5,5,4,6)
           2、对数据中的元素按照指定的规则进行过滤
            Array(1,4,7,9,10,6,8)
            规则: 只保留偶数数据
            结果: Array(4,10,6,8)
           3、对数据中的元素按照指定规则进行分组
            Array("zhangsan shenzhen man","lisi beijing woman","zhaoliu beijing man")
            规则: 按照地址进行分组
            结果： Map( shenzhen->List("zhangsan shenzhen man" ) , beijing->List( "lisi beijing woman","zhaoliu beijing man" ) )
           4、根据指定的规则获取数组中最大元素
            Array("zhangsan 30 3500","lisi 25 1800","zhaoliu 29 4500")
            规则: 获取工资高的人的信息
            结果: "zhaoliu 29 4500"
           5.根据指定规则对数组所有元素聚合
             	Array(10,4,6,10,2)
             	规则: 求和/求乘积
             	结果: 32
    匿名函数
    柯里化
    闭包
    递归
    惰性求值
    控制抽象
---面向对象-----------------------------------------------------------
    包
    类和对象【创建类，创建对象，定义属性，成员方法，构造器】
    类的特性:封装，继承，多态
    抽象
    单例对象
    伴生类和伴生对象（Object和class的结合体）
    特质Trait
---集合------------------------------------------------------------
    可变数组
    不可变数组
    可变数组和不可变数组互转
    多维数组【二维数组】

----------spark----------------
---day01---
wordCount
---day02---
1.rdd的创建方式:三种
2.Rdd的分区指定
---day03--
1.转换算子
    map、mapPartition、mapPartitionsWithIndex、flatMap、glom、groupBy、filter
    sample、distinct、coalesce、repartition、sortBy、pipe
1-1.题目：
    获取每个省份每个农产品的平均价格
2.双value值类型交互（两个RDD操作）
    intersection、union、subtract、zip
3.Key-Value类型
    partitionBy、自定义分区、 reduceByKey、groupByKey、



.....
--需求分析题---
1.Top10热门品类[点击数，下单数，支付数：下单数一样比较下单数，下单数一样，比较支付数]
2.Top10热门品类中每个品类的Top10活跃session[只统计点击]
3.页面单跳转化率统计
    从页面3-页面5就是单条，单条转换率=3-5的页面跳转/页面3的访问率