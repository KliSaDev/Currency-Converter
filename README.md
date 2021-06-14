Currency Converter
===

This application converts currencies in regard to base currency, which is **HRK** (croatian kuna).
API used for this project is an open source API provided by **HNB** (Croatian National Bank) and the project is adjusted according to it.
All of its endpoints can be found **[here](http://api.hnb.hr/)**.

Currency Converter contains three main screens.

* The first one shows the converter itself where user can choose any of the given currencies and convert it to HRK and vice versa.
* On the second screen user can see a list of all currencies along with their buying, middle and selling rate (in relationship to HRK).
* The third screen shows a graph of a selected currency and its middle rate values throughout the last seven days.

![alt text](https://user-images.githubusercontent.com/67363186/102076816-9d05de00-3e08-11eb-9979-413addf71bf2.png)&nbsp; &nbsp; &nbsp;![alst text](https://user-images.githubusercontent.com/67363186/102076823-a000ce80-3e08-11eb-8a54-4a4bfa027201.png)&nbsp; &nbsp; &nbsp;![alt text](https://user-images.githubusercontent.com/67363186/102076826-a131fb80-3e08-11eb-900e-54ee69f37130.png)

#### Libraries used:
* [Retrofit 2](https://square.github.io/retrofit/)
* [Dagger 2](https://dagger.dev/)
* [Rx](https://github.com/ReactiveX/RxKotlin)
* [Room](https://developer.android.com/topic/libraries/architecture/room?gclid=CjwKCAiAt9z-BRBCEiwA_bWv-NKy4ajMPu_hlTG4iSI6H2mevv7tM9K6kprTq1TNpDWxQQvOMhxDGBoCvogQAvD_BwE&gclsrc=aw.ds)
* [Moshi](https://github.com/square/moshi)
* [MP Android Chart](https://github.com/PhilJay/MPAndroidChart)
