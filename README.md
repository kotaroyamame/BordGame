# BordGame(五目並べ)
五目並べの連珠の人工知能開発を目指します。

## jarファイルダウンロード

[Download](http://iidesign.website/jar/KotaroGomoku.jar)

[jreのダウンロードはここから](https://java.com/ja/download/)

#それぞれのクラスについて

## Shinpan
審判は禁じ手、勝敗の判定をします

[連珠のルール参考リンク](http://qiita.com/YSRKEN/items/81f97660be023add2265)

## Bord
ボードは置かれた石の位置を保持し、マス目を描画します。先手、後手の順も管理します。

## Computer
数手先読みし、最善の位置を判断し、石を置きます。
四三、三三、四を判定し、それぞれの状態に優先順位をつけ、最も優先順位の高い所に石を置きます。

##AI
「勝つ」という目標だけを与え、深層学習により最終的に最善の手を打つ様になるようにします
