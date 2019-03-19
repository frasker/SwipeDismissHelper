# SwipeDismissHelper
支持Android侧滑退出，类似iOS效果

<img src="https://github.com/frasker/SwipeDismissHelper/blob/master/captures/68A79288C64A925019635523FC10368E.jpg" width="30%">


## 设置方式
在setContentView后设置
在Android5.0下需要配置android:windowIsTranslucent = true才可以生效
```
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwipeDismissHelper swipeDismissHelper = new SwipeDismissHelper(this);
	swipeDismissHelper.enable();
    }
}
```
## 支持自己处理侧滑逻辑
```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwipeDismissHelper swipeDismissHelper = new SwipeDismissHelper(this, new 	 SwipeDismissLayout.OnSwipeProgressChangedListener() {
            @Override
            public void onSwipeProgressChanged(float translate) {
                //TODO
            }

            @Override
            public void onSwipeCancelled() {
		//TODO
            }
        });
	swipeDismissHelper.enable();
    }
}
```
## 依赖

```
implementation 'com.frasker:swipedismiss:0.0.1'
```
