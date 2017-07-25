
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


## 讨论
邮箱：byhieg@gmail.com







