# 使用方法
## 方式1（推荐）
在模块级build.gradle添加如下：
```groovy
implementation 'com.github.touda.customview:customview:1.0.1'
```
## 方式2
直接下载[aar](https://github-registry-files.githubusercontent.com/738607523/e634ab00-abac-11ee-9f9f-41accda40290?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20240105%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240105T060423Z&X-Amz-Expires=300&X-Amz-Signature=8fb0a4dbb793b7d459bae410d9a6ee868cf0acec843373dced291b2f8f125fff&X-Amz-SignedHeaders=host&actor_id=0&key_id=0&repo_id=738607523&response-content-disposition=filename%3Dcustomview-1.0.1.aar&response-content-type=application%2Foctet-stream)包，
放入工程libs目录里，添加路径

从右边的Packages 进入，进行下载
![](screenhost/packages.png)
![](screenhost/customview_assets.png)

两种方式，可能还需要引入的库
```groovy
implementation 'com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4'
```

# 功能说明
```java
// 调试信息开关
setDebug(true)

// 设置固定试图
setPinned(-1, view) {section, v, b-> }

// 设置item偏移
setItem(0)

// 设置固定数据
setPinned(PinnedBean(一维数据源, 子项数据列表, 子项占位个数列表))

// 自动滚动到索引位置
startPinned(需要滚动的位置)
```

## 详细使用见demo