# HatenaView

Hatenaの記事フィード用アプリ

## 開発方針

### ブランチリスト

| ブランチ名 | 役割 |
| :---- | :---- |
| master | リリース済みブランチ、最新版の商用ソースコードです |
| develop | 開発用ブランチ、開発版の最新ソースコードです |
| feature/* | 機能開発ブランチ、Issue番号に紐づけられ、開発完了後に developブランチに取り込まれます |

### マージ方向

* develop から master へマージ（この際に developブランチは削除しない）
* feature/* から develop へマージ（この際は feature/* ブランチを削除）

### コミットメッセージの規則

以下のようにコミットメッセージに記述してコミットする。

* プレフィックスに ```[add]``` などをつける
* Issueと関連するコミットの場合は ```refs #15``` のようにチケット番号をつける
* コミットは基本的に一行で記述（説明が必要な場合のみ詳細な説明を別の行で記述）

例は以下の通り

```
[add] refs #15 新しいファイルの追加
```

| プレフィックス | 用途 |
| :--- | :--- |
| add | 新規機能追加 |
| update | 機能改修（バグ以外） |
| fix | バグ修正 |
| remove | 削除 |
| clean | リファクタリング |

### バージョンの規則

本アプリケーションでは以下の命名規則でバージョンを管理する

* versionNameは1.0.1と3つ区切りとする。
* versionCodeは5桁または6桁でversionNameを表す。

| versionName | versionCode |
| :---- | :---- |
| 1.0.3 | 10003 |
| 2.23.25 | 22325 |
| 13.21.56 | 132156 |

#### 各要素の考え方

| 要素 | versionName | versionCode | 備考 |
| :---- | :---- | :---- | :---- |
| メジャーバージョン | 1.0.0 | 10000 | 大幅なUI変更、機能変更など |
| マイナーバージョン | 0.1.0 | 100 | 入出力に関わる要素の変更、プッシュ機能追加、APIの追加、画面追加など） |
| リビジョン | 0.0.1 | 1 | バグ修正、細かいUI変更など |

## アーキテクチャ構成

### MVVMフレームワーク

本プロジェクトではMVVMフレームワークを採用して開発することとした。
下の図に表す通り、アプリケーションの構成をView / ViewModel / Modelに分離する構成としている。

![](./doc/img/framework.png?raw=true)

#### View

Viewは以下の処理を行うこととする。

* ViewModelで保持しているデータの状態に応じてUIを更新する
* View側の入力内容をViewModelへ伝える
* Viewで発生したイベント（タップなど）をViewModelで処理するようにイベント処理の責務を委譲する

#### ViewModel

ViewModelでは以下の処理を行うこととする。

* ViewのUI表示のために必要なデータの状態を保持する
* Viewから受け取ったイベントに応じてModelからデータを取得するための処理を呼び出す
* Modelから取得したデータの内容に応じて変更通知のイベントをViewへ送る

#### Model

Modelでは以下の処理を行うこととする。

* 外部のAPIと通信をしてデータを取得する
* DBからデータを取得する

### クラス構造

上記のMVVMフレームワークを基に下記のクラス構造で開発を進める。
各クラスの役割は以下の通り

![](./doc/img/class_structure.png?raw=true)

#### View

##### XML

* Activity / Fragment / RecyclerViewの要素を描画するためのXMLファイル

##### Activityクラス

* AndroidのActivityを制御する

##### Fragmentクラス

* AndroidのFragmentを制御する

##### Adapterクラス

* AndroidのReclerViewを描画する領域を制御する（データの構造に応じて適切な要素を振り分ける）

#### ViewModel

##### ViewModelクラス

* Activity / Fragmentで制御するXML要素へ描画するデータの管理を行う

##### Translatorクラス

* Model要素から取得したデータをView要素へ表示しやすいようにデータを整形するクラス

##### Dto（Data Transfer Object）クラス

* Translatorクラスを仲介してView要素で描画しやすいようにオブジェクトを整形したオブジェクトクラス

#### Model

##### Repositoryクラス

* DBとAPI（RSS）からデータを取得する処理を制御するクラス

##### Daoインタフェース

* DBに格納されているデータを取得するためのインタフェース

##### Serviceインタフェース

* API（RSS）からデータを取得するためのインタフェース

##### Entityクラス

* DBの要素を定義したオブジェクトクラス

##### Pojo（Plain Old Java Object）クラス

* API（RSS）から取得する要素を定義したプレインなオブジェクトクラス

## アプリの構成

### 利用するAPI（RSS）

| 機能名 | URL | パラメータ例 |
| :--- | :--- | :--- |
| 最新記事API | http://b.hatena.ne.jp/hotentry/{$param}.rss | all,social,etc. |
| タグ別記事API | http://b.hatena.ne.jp/search/tag?q={$param}&users={$param}&page={$page}&mode=rss| game,etc. |

## 参考文献

1. アプリのアーキテクチャ ガイド  _  Android Developers, https://developer.android.com/jetpack/docs/guide, Online; accessed 20-April-2019.
2. Android Architecture Components 初級 （ MVVM + LiveData 編 ） - Qiita, https://qiita.com/Tsutou/items/69a28ebbd69b69e51703, Online; accessed 20-April-2019.
3. DataBindingで実現するMVVM Architecture - Speaker Deck, https://speakerdeck.com/star_zero/databindingteshi-xian-surumvvm-architecture?slide=13, Online; accessed 20-April-2019.
4. Android開発でMVVMを採用してみて - Qiita, https://qiita.com/rmakiyama/items/779cf6407f70b40e4ee7, Online; accessed 20-April-2019.
5. Y.A.M の 雑記帳_ ViewPager + Fragment で AAC の ViewModel を使う, http://y-anz-m.blogspot.com/2018/04/viewpager-fragment-aac-viewmodel.html, Online; accessed 20-April-2019.
