# CLIENT Sample

https://github.com/backpaper0/simple-api-sample のクライアントさんです。

## 設定

`application.yml` に書いてるので、必要に応じて上書きしてね。

* `sample.api.url`
  * サーバーのURLを設定する
* `sample.userId`
  * ユーザーIdを設定する
  * サーバーで登録したユーザーIDを指定する。
  * 登録されていないユーザーIDで投稿しても、しれっと無視されます。

## 動かし方

```
gradlew bootRun
```

## 画面表示

Browse http://localhost:8090

タイムライン表示も投稿も全部この画面です。

