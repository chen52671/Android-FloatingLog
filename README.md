# FloatingLog
It is a lite library to show custom logs on screen for Android.

## Preview
![ScreenShot](https://github.com/chen52671/FloatingLogger/blob/master/screenshot/device-2016-12-16-182932.png)
## NOTICE
* 使用时需要打开应用设置中的“悬浮窗权限”(Open Permission of “Display pop-up window” of your app)


## USAGE
**STEP1** 在你的Application中调用`FloatingLog.init()` (Call `FloatingLog.init()` in your Application.java).
```java
        FloatingLog.init(this,true,true);
```

**STEP2** 通过调用FloatingLog.v 等来将log打印到悬浮屏幕上（Call FloatingLog.v .etc to print logs on floating view）.
```java
    FloatingLog.v(TAG, "V log");

    FloatingLog.d(TAG, "D log");

    FloatingLog.i(TAG, "I log");

    FloatingLog.w(TAG, "W log");

    FloatingLog.e(TAG, "E log");
```

**STEP3** 你可以通过通知栏控制是否显示该浮动log窗口，并可以决定它是否可滑动，过滤哪些特定的Log (Via the Notification You can hide the floating logs , decide whether the floating logs touchable and filter specific kind of log)
![ScreenShot](https://github.com/chen52671/FloatingLogger/blob/master/screenshot/device-2016-12-16-183000.png)
