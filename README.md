
![Alt text](https://github.com/byhieg/easyweather/blob/master/images/title.png)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Github: fork](https://img.shields.io/badge/GITHUB-Fork-green.svg)](https://github.com/byhieg/easyweather)
[![Github: follow](https://img.shields.io/badge/GITHUB-Follow-red.svg)](https://github.com/byhieg)

## 说明

简易天气项目有三个分支。

- master 系统无BUG可以完美运行的分支。
- feature2.0 采用MVP架构，不断优化 尝试新功能的分支
- MVC 不可修改的 采用MVC架构去实现

每一个分支都一个README。

## 功能

简易天气的功能有：

1. 查询城市数据，添加删除喜欢的城市。

2. 根据百度定位，确定用户当前所在位置。

3. 首页显示天气资讯，如 未来天气，运动指数，穿衣指数，感冒指数，以及未来天气走势。

4. 支持分享天气给他人。

5. 支持夜间模式，桌面天气控件.

6. 支持查询常见的天气百科，如雷阵雨，风力等级等含义。

## 安装
暂无应用市场上架。
目前仅支持clone代码，通过Android-Studio运行安装。
```
git clone https://github.com/byhieg/easyweather.git
```

## 最低配置
为了更好地展示App的效果，简易天气App最低支持版本为Android 4.4

## 功能截图
在相应的MVP，MVC的分支README中。

## 简易天气架构

第一版 采用传统的MVC搭建整个App，网络请求的部分采用自己封装的Okhttp，页面采用Activity来承载。Activity之间的通信采用本地广播+Handler的形式。

### MVC的缺点
缺点明显：首先MVC是M代表Model层，V代表View层，C代表逻辑控制层。Model表示模型层，代表是数据结构以及与数据相关的操作行为，如如何去数据，存数据。
V代表视图，在Android中就是我们写的各种View,ViewGroup，其功能在于响应用户的各种触摸点击事件，并将用于的请求传递给C。C表示控制层，在Android表示Activity或者Fragment，其功能在于接受用户的请求，去操作Model来更新数据。
整体的流程就是View响应用户的各种事件，然后将请求传递给Controller，Controller去更新Model数据，然后Model将更新的数据直接显示在View层。

但是在Android当中，充当Controller的Activity和Fragment，同时也充当了View的功能。即Activity和Fragment既要负责View的各种事件处理，还要执行属于Controller的逻辑，并且和Model层也有耦合。
整体的功能太过累赘。对于修改和扩展难度也比较大。

在此情况下，尝试采用MVP

### 第二版——MVP

MVP中M和V还是与MVC中一样，而P层则全面处理View事件的转发。Activity与Fragment则作为了View层。View层负责响应各种触摸与点击事件，并将用户的请求转发给P层去处理，P层则负责与Model层进行交互，来更新数据，并将更新后的内容通知View层去改变。
这样的话，V和P是有联系的，而P只持有Model的引用。View与Model是没有联系的。而View与Presenter之间的联系是接口来实现的。这样View可以对应不只一个Presenter，而Presenter也对应不只一个View.

缺点就是：在项目当中，Activity是作为创建View层与Presenter层的一个控制器，真正的View层是Fragment，通过Activity将View与Presenter结合起来。所以项目中类太多，接口太多。在简易天气中，针对每一个功能，都有个Activity，一个Fragment，一个接口类，内部定义了View接口和Presenter接口，以及一个Presenter实现类。基本每个功能模块都要有这几个类，接口太多，类太多。所以针对我这种需求的App，其实MVP不没有绝对的选择优势。但在那种多个页面都相同，但业务逻辑不通的App时，即一个View多个Presenter时，MVP就很试用，可以一定程度减少接口和类的过多。同时在一个Presenter多个View的情况下，也可以试用MVP。

第二版中，所用的功能模块的通信全部采用的EventBus,取消掉本地广播+Handler形式，减少代码上的臃肿。

对于Model采用封装的形式 即Model层，分成网络获取的model和本地的存储的model，所有P层获取的数据全部都是通过本地存储Model去提供的。由于不想直接让P层去与真正的Model类耦合，所以采用静态代理的形式。这样，真正操作的类即使有修改 也不会直接影响到Presenter当中。

网络层这边，我直接封装的retrofit。

## 对简易天气的性能优化

### gradle 编译优化

通过

```
gralde build -profile
```
记录gradle 性能

![](https://github.com/byhieg/easyweather/blob/5e3c6a793588c70a487438c0e9ef004fbb5055e0/images/gradle%E6%80%A7%E8%83%BD.jpg)

可以看出gradle大头的执行时间全部在Task Execution。我这边有三个模块，一个是app模块，一个是网络请求的模块，一个是监控模块。

看一下每个模块，占大头的任务是哪个

```
:monitor	0.562s	(total)
:monitor:processReleaseResources	0.485s

```
```
:app	22.244s	(total)
:app:transformClassesWithDexForDebug	17.429s
:app:compileDebugJavaWithJavac	1.408s
:app:mergeDebugResources	0.851s
:app:processDebugResources	0.631s
:app:clean	0.214s
:app:greendao	0.190s
```

```
:betterload	0.569s	(total)
:betterload:processReleaseResources	0.478
:betterload:mergeReleaseResources	0.046s	UP-TO-DATE
```

可以看到对于直接导入的module betterloadNet 与monitor都非常占用gradle时间，这里对于betterloadNet采用导出arr包，在引用的形式，在此测试gradle性能。

```
betterloadNet	0s	(total)
```
这边是不会再对betterloadNet执行任务了
对于监控模块也同样处理，就不多说了。

而对于app模块，主要的时间还是在aapt上面，As支持对debug版本采用aapt采用一定优化，但对于realse版本却不能用，应为资源没有经过aapt优化 会有问题。

所以为了提高aapt 则采用并行任务执行的方式

```
:app	3.361s	(total)
:app:compileDebugJavaWithJavac	1.027s
:app:mergeDebugResources	0.896s
:app:processDebugResources	0.738s
```

可以增加一点速度。

此外，上面monitor模块中一个task 执行时间很长，可以在build.gradle中，关闭该task的执行

```
tasks.whenTaskAdded{ task->
    if (task.name.contains("mockableAndroidJar")){
        task.enabled = false
    }
}
```
使用上面的代码 在monitor的build.gradle。针对app中有些任务可以这么执行

因为该项目功能比较简单，涉及到的模块比较少，因此gradle编译的时间不会很长。但对于gradle编译的速度的优化，基本都大同小异，能做的有限。


如果在编译之后，在编译一次，时间会大大缩短。缩短的原因，是因为已经生成了一些build，app这个模块省略了transformClassesWithDexForDebug的时间。

在这里，针对transformClassesWithDexForDebug过程是最耗费时间，但还必须要执行class转换成dex的过程。所以针对第一个build，是比较无能为力的。但一旦我们修改一些java代码,在此gradle build又会执行transformClassesWithDexForDebug。

下面的只是我的一些想法，采用类似腾讯热补丁的技术，第一个生成的dex不做优化，但对于再次改动还会执行transformClassesWithDexForDebug的操作，则采用仅针对改动的部分转换成dex，其他的部分不变，这样在一定程度上会解决问题。

但要解决的问题很多：

1. 首先要对比文件的变化
2. 缓存生成的dex
3. 按照dex的规律去放在transformClassesWithDexForDebug生成后的目录

但目前对于我这个项目，这个优化，就先放下了。

### 启动优化

首先通过adb命令查看启动到第一个过度透明Activity的时间

`
adb shell am start -W "com.weather.byhieg.easyweather/com.weather.byhieg.easyweather.startweather.SplashActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
`
没优化前 启动时间

```
Starting: Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] cmp=com.weather.byhieg.easyweather/.startweather.SplashActivity }

Status: ok
Activity: com.weather.byhieg.easyweather/.startweather.StartActivity
ThisTime: 496
TotalTime: 676
WaitTime: 709
Complete
```
WaitTime是系统启动App的Activity的总时间
TotalTime是系统启动Activity所耗时间，比WaitTime小，因为WaitTime还包括之前Activity的onPause,调用AMS的时间。
ThisTime是最后一个Activity启动的耗时。

这边，简易天气是有一个透明的过渡页。SplashActivity是没有执行setContentView，直接调转到StartActivity，这个StartActivity是展示一个简易天气动画的页面。

这边主要优化的是Application启动时间过长，首先编写时间监视器，来检测每个关键的时间点执行的时间。

```
D/TimeMonitor: ApplicationCreated:80
D/TimeMonitor: SplashActivity_create:90
D/TimeMonitor: SplashActivity_create_over:118
D/TimeMonitor: StartActivity_create:129
D/TimeMonitor: StartActivity_start:204
```

可以看出，Appliction初始化时间占据了大头，对Applicaition初始化优化主要工作:

对于数据库或者网络等组件，采用异步初始化，或者懒加载，尽快结束Application初始化

```
D/TimeMonitor: ApplicationCreated:47
```

上面是针对数据库采用懒加载的用的，只用真正存库的时候，才会初始化数据库。
针对网络请求框架，采用异步加载的形式，

```
/TimeMonitor: ApplicationCreated:10
```
最后Application onCreate方法执行完，时间是10ms

然后针对startActiity的create时间优化，这一切都是为了尽快看到StartActivity的

在这边，移除掉SplashActivity，之前用SplashActivity是因为避免启动白屏，而启动该Activity,然后在从该Activity中，启动StartActivity。这边，直接设置StartActity的主题为透明，让StartActivity充当过度的页面。

```
D/TimeMonitor: StartActivity_create:60
StartActivity_start:131
```
可以看到节省了很多时间，但StartActivity的create执行时间还是太多。
修改StartActivity的主题，因为启动的动画是绿色的背景，所以直接修改该Activity的主题是绿色。
通过优化布局，因为之前RelativeLayout中只有一个View，在设置主题之后，不需要父布局，所以删除所有父布局，直接让activity.xml只有一个View

在设置Theme之后，点击桌面icon会极快速度出现app，原因是因为theme中设置的是windows的主题，
而window的创建是在Activity被反射创建之后，在activity的attach方法中，被创建。
我们经常setContentView，实际上是由window来调用的。这边，具体说一下setContentView

setContentView, ，执行installDecorView
在installDecorView中，会用过generateLayout 来生成一个布局，generateLayout中就会解析Theme，根据设置的windowBackGround来设置背景。
在生成DecorView之后，才会将我们写的布局放入到DecorView的contentView中。

这就是设置的Theme会出现在我们写的布局之前出现的原因。

```
StartActivity_create_setContentView_start:58
StartActivity_create_setContentView_stop:78
```
可以看出，setContentView在78ms时执行完毕。
在优化下不合理的代码，最后执行到onStart生命周期的时间大概在

```
D/TimeMonitor: StartActivity_start:112
```
再来看adb shell

```
Starting: Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] cmp=com.weather.byhieg.easyweather/.startweather.StartActivity }

Status: ok
Activity: com.weather.byhieg.easyweather/.startweather.StartActivity
ThisTime: 315
TotalTime: 315
WaitTime: 332
Complete
```
比起之前的，totalTime优化了近50%

### 内存泄漏

内存泄漏是指本该被回收的对象，却因为被其他对象强引用，而不会被回收，一直存活。

这里 首先尝试利用MAT工具，先利用Android Device Monitor，搜集简易天气的hprof 文件，然后倒入到MAT，进行分析。

可以看到

![](https://github.com/byhieg/easyweather/blob/99e85f2e89cdcfd5e20cb67988d3b1e04a7ab1ed/images/MAT%E5%86%85%E5%AD%98%E6%B3%84%E9%9C%B2%E5%9B%BE.jpg)

MAT功能很强大，但在这里用LeakCanary来真正分析内存泄漏图

#### LeakCanary 内存泄漏


- 通过leakCanary发现在CityManagerActivity的页面，出现内存泄漏

>原因是Presenter对象，被设置为static，而presenter对象需要持有view，而View又因为context的缘故而持有Acivity,所以就导致Activity无法被回收。
>当初设置static的原因是，需要一个Static Handler的类，在handleMessage的时候，通过presenter来执行逻辑，出于省事，就是直接设置了presenter为static。

>现在全部采用EventBus来处理页面之间的消息传递。

- 在进行BackService中，出现内存泄漏。

>对错误的提示，采用Toast提示，自己的封装的Toast类，采用单例模式，这样，就导致单例持有context，而Context则设置的是BackService.this。修改方式是对于Context，采用Application,使封装的Toast不持有对BackService的引用，这样就不会内存泄漏。

- ConnectManager 出现的内存泄漏

> 在MainActivity中，需要针对网络变化做检查，引入了Connectmanager，通过this去getSystemService。在查过资料后，发现Connectmanager 在6.0以上被设置为单例模式，并且需要传入一个Context。在通过this去getSystemService的时候，就会发生单例模式去持有这个Context，也就是this。导致MainActivity无法被回收，发生了内存泄漏。
在5.1上ConnectivityManager实现为单例但不持有Context的引用，在5.0有以下版本ConnectivityManager既不为单例，也不持有Context的引用。

- ProvinceFragment/CityFragent 出现的内存泄漏

在这两个Fragment中，都是用到了ListView，对于listView的adapter，传入的context是当前的Activity,因此造成内存泄漏。这边，采用的方式同样是传入Applicaition

- 百度地图listener 出现的内存泄漏

> 在覆写百度地图的Listener时，需要显示对话框，这边传入的是Activity，但Dialog的代码在Listener里面，并且将Dialog设置为listener的成员变量，这样就导致了Activity泄漏。
> 这次，修改的办法就不能将ApplicationContext传入了，因为Dialog需要一个Activity的token。并且整个逻辑应该是放到HomeFragment中，通过presenter去执行逻辑。所以这边，直接用了EventBus来通知HomeFragment去显示以及处理逻辑。

至此，整个简易天气内存泄漏方面就处理完毕。


### 绘制与卡顿优化

这边，利用的工具是TraceView，首先，找到明显卡顿的地方，通过TraceView分析，方法调用，执行的时间。

TraceView有两种用法，一种是利用DDMS中，开启traceView，另一种是在代码中，调用

```
Debug.startMethodTracing();
Debug.stopMethodTracing();

```

在简易天气之中，点击分享的时候，会非常卡，经过traceView分析，在进入该Activity时，在Oncreate中，调用一个绘制图片的方法，该方法耗时过长。导致进入该Activity时间过长。

解决办法：

将绘制的图片，保存成图片，然后在首页，采用异步线程去绘制。这里，是用的EventBus，background方法

然后在分享的，启动的Activity直接读取绘制后的图片，而不是每次启动都会现绘制。


在首页中，当刷新之后，快速下滑到曲线图时，会出现明显的卡顿，Logcat显示跳过50多帧。

经过TraceView分析，曲线图时采用自定义View绘制的，在onDraw方法之前，需要在主线程计算要绘制的数据，造成下滑时，数据还没有计算好。从而造成卡顿、

解决的方法：

1. 在异步进程计算数据，然后计算好后在通知view重绘。

## 讨论
邮箱：byhieg@gmail.com







