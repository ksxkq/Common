package com.ksxkq.common;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * OnePiece
 * Created by xukq on 10/8/16.
 *
 * Note:
 * merge: 合并两个 Observable,两条线合并成一条线,数据类型可以是一样,也可以不一样。
 * zip: 合并成一个新的
 */

public class RxJavaActivity extends AppCompatActivity {

    // 场景零：线程切换
    private void getContactFromDB() {
        Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                //模拟从数据库中获取数据
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    list.add("user name:" + i);
                }
                //模拟耗时操作
                SystemClock.sleep(5000);

                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> list) {
                        Log.d("MainActivity", "更新界面：" + list.size());
                    }
                });
    }

    // 场景一：接口依赖(flatmap)
//    private void handleLogin(LoginPost post) {
//        ApiFactory.getBaseApi().login(post).flatMap(new Func1<Result<Token>, Observable<Result<User>>>() {
//
//            @Override
//            public Observable<Result<User>> call(Result<Token> tokenResult) {
//                if (tokenResult.isOk()) {//获取token成功
//                    Token data = tokenResult.getData();
//                    String token = data.getToken();
//                    //保存token操作
//                    return ApiFactory.getUserApi().getUserProfile(token);//获取用户信息
//                } else {//获取token，直接触发onError()方法
//                    return Observable.error(new ApiException(tokenResult.getCode(), tokenResult.getMsg()));
//                }
//            }
//        }).subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        showWaitDialog();
//                    }
//                }).subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<Result<User>>(this) {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onNext(Result<User> userResult) {
//                        //处理用户信息
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        、//处理错误
//                    }
//                });
//
//    }

    // 场景二：接口合并(merge)
//    很多情况下，一个界面中需要的数据来自多个数据源（请求），而只有当所有的请求的响应数据都拿到之后才能渲染界面。
//
//    接口结果同类型
//
//    当前数据源来自多个渠道，拿到的结果属于同一类型的，比如有些数据需要从本地数据读取，而另一些数据则从网络中获取，但无论哪个数据源今最后返回的数据类型是一样的，比如：
//
//    private Observable<ArrayList<String>> getDataFromNet() {
//        ArrayList<String> list = new ArrayList<>();
//        for(int i=0;i<10;i++) {
//            list.add("data from net:" + i);
//        }
//
//        return Observable.just(list);
//    }
//
//    private Observable<ArrayList<String>> getDataFromDisk() {
//        ArrayList<String> list = new ArrayList<>();
//        for(int i=0;i<10;i++) {
//            list.add("data from disk:" + i);
//        }
//
//        return Observable.just(list);
//    }
//
//    上面的两个方法分别从磁盘和网络中获取数据，且最后的数据类型都是ArrayList<String>,现在我们来合并这两个接口：
//
//    private void getData() {
//        Observable.merge(getDataFromDisk(), getDataFromNet()).subscribe(new Subscriber<ArrayList<String>>() {
//            @Override
//            public void onCompleted() {
//                //更新界面
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(ArrayList<String> list) {
//                for (String s : list) {
//                    Log.d("MainActivity", s);
//                }
//            }
//        });
//
//        接口结果不同类型
//
//        有些情况下，不同数据源返回的结果类型不一致，那该如何解决呢？比如当前存在两个接口：
//
//        @GET("dict/locations")
//        Observable<Result<ArrayList<String>>> getLocationList();
//
//        @GET("user")
//        Observable<Result<User>> getUserInfo(@Query("id") String id);
//
//        只有当这两个请求都完成后才能更新UI，那我们该怎么做呢？同样还是使用merge操作符，关键在于如何区分响应：
//
//        private void getData(String uid) {
//            Observable<Result<ArrayList<String>>> locationOb = ApiFactory.getUserApi().getLocationList();
//            Observable<Result<User>> userOb = ApiFactory.getUserApi().getUserInfo(uid);
//
//            Observable.merge(locationOb,userOb).subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<Result<? extends Object>>() {
//                        @Override
//                        public void onCompleted() {
//                            //更新UI
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onNext(Result<? extends Object> result) {
//                            Object data = result.getData();
//                            if(data instanceof User ){
//                                //处理用户数据
//                            } else if (data instanceof ArrayList) {
//                                //处理位置列表
//                            }
//                        }
//                    });
//
//        }

//    场景三：构建多级缓存（concat）
//
//    缓存机制想必是众所周知。这里我们就以常见的三级缓存机制为例：首先从内存中获取数据，如果内存中不存在，则从硬盘中获取数据，如果硬盘中不存在数据，则从网络中获取数据。现在看看RxJava是如何帮我们解决这个问题：
//
//    //获取数据
//    private void getData(String url) {
//
//        Observable.concat(getDataInMemory(url),getDataInDisk(url),getDataInNet(url)).takeFirst(new Func1<Bitmap, Boolean>() {
//            @Override
//            public Boolean call(Bitmap bitmap) {
//                return bitmap!=null;
//            }
//        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Bitmap>() {
//            @Override
//            public void call(Bitmap bitmap) {
//                //处理图片
//            }
//        });
//    }
//
//    //从内存中获取
//    private Observable<Bitmap> getDataInMemory(final String url) {
//        final Map<String, Bitmap> memoryCache = new HashMap<>();
//        //模拟内存中的数据
//        //...
//
//        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
//
//            @Override
//            public void call(Subscriber<? super Bitmap> subscriber) {
//                if (memoryCache.containsKey(url)) {
//                    subscriber.onNext(memoryCache.get(url));
//                }
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    //从硬盘中获取
//    private Observable<Bitmap> getDataInDisk(final String url) {
//        final Map<String, Bitmap> diskCache = new HashMap<>();
//        //模拟内存中的数据
//        //...
//
//        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
//
//            @Override
//            public void call(Subscriber<? super Bitmap> subscriber) {
//                if (diskCache.containsKey(url)) {
//                    subscriber.onNext(diskCache.get(url));
//                }
//                subscriber.onCompleted();
//            }
//        });
//
//    }
//
//    //从网络中获取
//    private Observable<Bitmap> getDataInNet(final String url) {
//        return Observable.create(new Observable.OnSubscribe<Bitmap>(){
//
//            @Override
//            public void call(Subscriber<? super Bitmap> subscriber) {
//                Bitmap bitmap=null;
//                //从网络获取图片bitmap
//
//                subscriber.onNext(bitmap);
//                subscriber.onCompleted();
//            }
//        }).subscribeOn(Schedulers.io());
//    }
//
//    rxjava为我们提供的concat操作符可以很容的的实现多级缓存机制。这里需要记住在getData()方法中不要忘记使用takeFirst()。concat操作符接受多个Observable，并按其顺序串联，
//    在订阅的时候会返回所有Observable的数据（按顺序依次返回）。换言之，如果在getData（）中不实用takeFirst（）,将会并行的从内存，硬盘及网络中检索数据，这显然不是我们想要的。takeFirst操作符可以从返回的数据中取出第一个，并中断数据检索的过程。我们知道，检索速度：内存>硬盘>网络，这就意味着当我们从内存中获取到数据的时候就不会再从硬盘中获取数据，反之，则从硬盘中获取数据；当我们从硬盘中获取到数据的时候就不会再从网络中获取到数据了，反之，则从网络中获取。
//
//    这样就实现了我们的最终目标。

//    场景四：定时任务（timer）
//    在一些情况下我们需要执行定时任务，传统的做法上有两种方式可选择：Timer和SchelchExector。但是在引入rxjava之后，我们有了第三种选择:

    private void concat() {

    }

    private void startTimerTask() {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("MainActivity", "start execute task:" + Thread.currentThread().getName());
            }
        });
    }

//    场景五：周期任务（interval）
//    当然rxjava通过interval提供了周期任务的支持：

    private void startIntervalTask() {
        Observable.interval(5, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("MainActivity", "start task:" + Thread.currentThread().getName());
            }
        });
    }

    //    场景十：响应式界面
//
//            界面元素更新
//
//    在信息填充界面时，我们经常会遇到只有填写完必要的信息之后，提交按钮才能被点击的情况。比如在登录界面时，只有我们填写完用户名和密码之后，登录按钮才能被点击。通过借助rxjava提供的combineLatest操作符我们可以容易的实现这种响应式界面
//
//    EditText mEditUsername = (EditText) findViewById(R.id.editText3);
//    EditText mEditPwd = (EditText) findViewById(R.id.editText4);
//    final Button mBtnLogin = (Button) findViewById(R.id.button2);
//    mBtnLogin.setEnabled(false);
//
//    Observable<CharSequence> usernameOb = RxTextView.textChanges(mEditUsername);
//    Observable<CharSequence> pwdOb = RxTextView.textChanges(mEditPwd);
//
//    Observable.combineLatest(usernameOb, pwdOb, new Func2<CharSequence, CharSequence, Boolean>() {
//        @Override
//        public Boolean call(CharSequence username, CharSequence pwd) {
//
//            return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd);
//        }
//    }).subscribe(new Action1<Boolean>() {
//        @Override
//        public void call(Boolean isLogin) {
//            mBtnLogin.setEnabled(isLogin);
//        }
//    });
    private void combineLatest() {

    }

    //    Observable.zip(
//            service.getUserPhoto(id),
//            service.getPhotoMetadata(id),
//            (photo, metadata) -> createPhotoWithData(photo, metadata))
//            .subscribe(photoWithData -> showPhoto(photoWithData));
    private void zip() {

    }


    // 合并同类型的(可以是不同类型,参考上面)  mergerWith 实现和 merge 相同
    public static void mergeTest() {
        Observable<String> obs_0 = Observable.from(new String[]{"1", "2", "3"});
        Observable<String> obs_1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
        Observable<String> obsMerge = Observable.merge(obs_0, obs_1);
        obsMerge.observeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("debug", " onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("debug", " onError ");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("debug", " s = " + s);
                    }
                });

    }

}
