# 今日头条指示器文字效果

## Canvas中的一些重要方法
>1. 以drawXXX为主的绘制方法；
>2. 以clipXXX为主的裁剪方法；
>3. 以scale、skew、translate和rotate组成的Canvas变换方法；
>4. 以saveXXX和restoreXXX构成的画布锁定和还原；
>5. 其他

## Android画布剪裁函数clipRect详解
### 什么是clipRect？
clipRect函数是android.graphics.Canvas类下一个用于对画布进行矩形裁剪的方法。 
它裁剪了我们想要的绘制区域 ， 有点类似ps里面的遮罩效果 。

### clipRect的重载
![](.\assets\clip.png)

### Region.Op理解
```java
public static enum Op {
        DIFFERENCE,//DIFFERENCE 第一次不同于第二次的部分显示出来
        INTERSECT,//INTERSECT 交集显示 
        UNION,//全部显示
        XOR,//补集 就是全集的减去交集剩余部分显示
        REVERSE_DIFFERENCE,//是第二次不同于第一次的部分显示
        REPLACE;//是显示第二次的

        private Op() {
        }
    }
```

## saveXXX和restoreXXX


























