DROP DATABASE NoticeBoard;
CREATE DATABASE NoticeBoard;
USE NoticeBoard;

DROP TABLE IF EXISTS t_board;
CREATE TABLE `t_board` (
  `board_id` int NOT NULL AUTO_INCREMENT,
  `board_title` varchar(50) NOT NULL,
  `board_content` varchar(3000) DEFAULT NULL,
  `view_number` int NOT NULL DEFAULT '0',
  `register_user_id` varchar(18) DEFAULT NULL,
  `register_date` timestamp DEFAULT NULL,
  `update_user_id` varchar(18) DEFAULT NULL,
  `update_date` timestamp DEFAULT NULL,
  `delete_flag` varchar(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `t_board` VALUES 
(null,'「Keycloak」とは①','本連載では、近年注目されている認証プロトコル「OpenID Connect」をサポートするオープンソースのシングルサインオン（SSO）ソフトウェア「Keycloak」の活用方法を解説していきます。\r第1回目は、APIにおける認証／認可の仕組みとKeycloakの概要を紹介します。',0,'test','2023-06-01 09:00:00',NULL,NULL,'0'),
(null,'「Keycloak」とは②','近年、金融や流通分野で注目されている「APIエコノミー」や「マイクロサービスアーキテクチャ」などの登場により、サービスの機能を「REST API」として提供することが当たり前になってきています。そして、REST APIを公開するためには、誰がアクセスしてきたのかを確認するための「認証（Authentication）」と、APIへのアクセスを誰に許可するのかという「認可（Authorization）」の仕組みが不可欠です。',0,'test','2023-06-01 09:01:00',NULL,NULL,'0'),
(null,'「Keycloak」とは③','しかし、複数のサービスがそれぞれ個別に認証／許可を行うと、ユーザー名／パスワードといった認証情報やアクセス制御ポリシーの管理が煩雑になってしまいます。そこで、一度の認証で全てのサービスを利用できるようにする仕組み「シングルサインオン」が有用になってきます。',0,'test','2023-06-01 09:02:00',NULL,NULL,'0'),
(null,'「Keycloak」とは④','これまでの認証／認可のアーキテクチャでは、1つのサービスで認証を行った結果を「セッション」として保持しておき、そのセッションを用いてアクセスを制御すれば問題ありませんでした。ところが、マイクロサービスアーキテクチャのように複数のサービスが協調して動作するようなアーキテクチャでは、サービスごとに認証／認可を行う必要があるため、認証情報やアクセス制御ポリシーの管理が煩雑になってしまいます。',0,'test','2023-06-01 09:03:01',NULL,NULL,'0'),
(null,'「Keycloak」とは⑤','そこで、認可と認証の機能をAPIサーバから分離し、ユーザーが“認証済み”であることを示す認証結果やアクセス権限などの認可情報をAPIサーバ間で共有することで、認証情報やアクセス制御ポリシーを一元化し、管理を容易にすることができます。',0,'test','2023-06-01 09:04:03',NULL,NULL,'0'),
(null,'KeycloakのSSO動作を確認してみよう①','本連載では、近年注目されている認証プロトコル「OpenID Connect」をサポートするオープンソースのシングルサインオン（SSO）ソフトウェア「Keycloak」の活用方法を解説していきます。今回は、サンプルアプリケーションを使って、KeycloakによるSSOの動作を確認してみます。',0,'test','2023-06-01 09:06:46',NULL,NULL,'0'),
(null,'KeycloakのSSO動作を確認してみよう②','本連載では、近年注目されている認証プロトコル「OpenID Connect」をサポートするオープンソースのシングルサインオン（SSO）ソフトウェア「Keycloak」の活用方法を解説していきます。今回は、サンプルアプリケーションを使って、KeycloakによるSSOの動作を確認してみます。',0,'test','2023-06-01 10:07:10',NULL,NULL,'0'),
(null,'KeycloakのSSO動作を確認してみよう③','今回はCent OS 7.2 64bitを使用しますが、他のLinux系OSやWindows OS上でも実行可能です。\rまた、Keycloakのサンプルアプリケーションをビルド、デプロイするには「Java Development Kit（JDK）」および「Maven」が必要になりますので、適宜インストールを行ってください。',0,'test','2023-06-01 11:09:46',NULL,NULL,'0'),
(null,'KeycloakのSSO動作を確認してみよう④','Keycloakの使用方法\r　まずは、Keycloakを起動し、「Administration Console」（Keycloakの設定を行うWeb GUI）へアクセスするまでの手順を説明します。',0,'test','2023-06-01 12:10:46',NULL,NULL,'0'),
(null,'KeycloakのSSO動作を確認してみよう⑤','Keycloakの起動\r　Keycloakの本体は、ダウンロードサイト（http://www.keycloak.org/downloads.html）から「Standalone server distribution」を入手します。\r本稿ではバージョン「3.2.0.Final」を使用しています。',0,'test','2023-06-02 12:13:46',NULL,NULL,'0'),
(null,'KeycloakのAPIサービスを構築してみよう①','本連載では、近年注目されている認証プロトコル「OpenID Connect」をサポートするオープンソースのシングルサインオン（SSO）ソフトウェア「Keycloak」の活用方法を解説していきます。\r今回は、Keycloakクライアントアダプターを利用して、APIサービスを構築してみます。',0,'test','2023-06-02 13:22:46',NULL,NULL,'0'),
(null,'KeycloakのAPIサービスを構築してみよう②','連載第3回目となる今回は「Keycloak」が提供するクライアントアダプターを利用して、OpenID Connectに対応したAPIサーバとクライアントアプリケーションを構築します。\rKeycloakクライアントアダプターを利用すると、Keycloakと連携できるようになり、簡単にシングルサインオン（SSO）や公開APIサービスを実現することができます。',0,'test','2023-06-02 13:32:46',NULL,NULL,'0'),
(null,'KeycloakのAPIサービスを構築してみよう③','Keycloakクライアントアダプターについて\r　「クライアントアダプター」はKeycloakが提供するライブラリで、Keycloakからのアクセストークン取得やアクセス制御を行います（図1）。「Wildfly」や「Jetty」といったアプリケーションサーバ向けのアダプターや、「Spring Framework」などのフレームワーク向けのアダプターが提供されています。',0,'test','2023-06-02 14:42:46',NULL,NULL,'0'),
(null,'KeycloakのAPIサービスを構築してみよう④','今回は、WildflyアダプターとSpring Framework用アダプターを利用します。それ以外のアダプターを利用する場合は、Keycloakの公式ドキュメント「Securing Applications and Services」を参照してください。',0,'test','2023-06-02 15:52:46',NULL,NULL,'0'),
(null,'KeycloakのAPIサービスを構築してみよう⑤','今回構築するアプリケーションは、以下の図2のようになっています。なお、これらのサンプルはGitHubのリポジトリ（https://github.com/Hitachi/Keycloak-adapter-examples）で公開しているので、ぜひ活用してください。',0,'test','2023-06-03 08:02:46',NULL,NULL,'0'),
(null,'Keycloakのクラスタ環境を構成してみよう①','本連載では、近年注目されている認証プロトコル「OpenID Connect」をサポートするオープンソースのシングルサインオン（SSO）ソフトウェア「Keycloak」の活用方法を解説していきます。今回は、Keycloakのクラスタ環境を構成する方法を解説します。',0,'test','2023-06-03 08:12:46',NULL,NULL,'0'),
(null,'Keycloakのクラスタ環境を構成してみよう②','「Keycloak」を含むシステムを構築した際、Keycloakがダウンすると認可に関する情報を取得できなくなるため、システム全体がアクセス不能となってしまいます。これを防ぐためには、Keycloakサーバをクラスタ化し、システム全体の可用性を確保する必要があります。',0,'test','2023-06-03 08:22:46',NULL,NULL,'0'),
(null,'Keycloakのクラスタ環境を構成してみよう③','KeycloakではKeycloakを実行するためのアプリケーション（AP）サーバである「WildFly」が提供するクラスタ機能を利用して、ホスト間でデータを共有しています。また、セッション情報などのデータをクラスタ間で共有することで、ホストがダウンした際の処理を他のホストで引き継げるようになっています。',0,'test','2023-06-03 08:23:46',NULL,NULL,'0'),
(null,'Keycloakのクラスタ環境を構成してみよう④','Keycloakの設定項目\r　Keycloak自体が持つ設定情報や、Keycloak上で行ったレルムやクライアント設定など、GUIやCLIで設定した項目が共有されます。これらの情報は、外部のデータソースを経由して共有されます。',0,'test','2023-06-03 09:14:46',NULL,NULL,'0'),
(null,'Keycloakのクラスタ環境を構成してみよう⑤','セッション情報\r　Keycloakのセッション情報もホスト間で共有されます。これにより、ユーザーのログイン状態が維持されます。セッション情報は、WildFlyに搭載されている「JGroups」「Infinispan」を利用して共有されます。JGroups、Infinispanの詳細は、WildFlyのドキュメント（https://docs.jboss.org/author/display/WFLY10/Documentation）を参照してください。',0,'test','2023-06-03 09:15:34',NULL,NULL,'0'),
(null,'外部アイデンティティーと連携してみよう①','本連載では、近年注目されている認証プロトコル「OpenID Connect」をサポートするオープンソースのシングルサインオン（SSO）ソフトウェア「Keycloak」の活用方法を解説していきます。Keycloakは、OAuthやOpenID Connectに対応しており、GoogleやFacebookなどのユーザーを利用したSSOやアクセス制御が簡単に設定できます。',0,'test','2023-06-03 09:26:46',NULL,NULL,'0'),
(null,'外部アイデンティティーと連携してみよう②','はじめに\r連載第5回目となる今回からは、野村総合研究所のOSSサポートサービスのメンバーが執筆させていただきます。\r日立製作所の茂木氏が執筆した前回まででは、次のような内容を紹介してもらいました。',0,'test','2023-06-03 10:27:46',NULL,NULL,'0'),
(null,'外部アイデンティティーと連携してみよう③','今回からは数回に分けて、「Keycloak」の機能を幾つか紹介していきます。今回は、GoogleやFacebookなどのSNS（Social Networking Service）のユーザーを、Keycloakと連携するアプリケーションにシングルサインオンさせる方法について説明します。',0,'test','2023-06-03 11:28:46',NULL,NULL,'0'),
(null,'外部アイデンティティーと連携してみよう④','Webサービスのログイン動向\r　最近では、以下の画面1のように、SNSのアカウントを使ってログインできるWebサービスをよく見掛けるようになりました。',0,'test','2023-06-03 12:29:46',NULL,NULL,'0'),
(null,'外部アイデンティティーと連携してみよう⑤','Keycloakのアイデンティティー・ブローカー機能とは\r　アイデンティティー・ブローカーとは、「外部アイデンティティー・プロバイダー」（以下、外部IdP）のアカウントを使用して、Keycloakと連携しているアプリケーションにシングルサインオン（SSO）できるように仲介する機能です（図1）。',0,'test','2023-06-03 13:30:46',NULL,NULL,'0'),
(null,'外部ユーザーストレージに連携してみよう①','本連載では、近年注目されている認証プロトコル「OpenID Connect」をサポートするオープンソースのシングルサインオン（SSO）ソフトウェア「Keycloak」の活用方法を解説していきます。Keycloakは、Active Directoryなどの外部ユーザーストレージと連携し、それらで管理されたユーザーによるシングルサインオンを実現できます。',0,'test','2023-06-03 14:30:46',NULL,NULL,'0'),
(null,'外部ユーザーストレージに連携してみよう②','はじめに\r　近年は、ユーザー情報をクラウド上で管理する「IDaaS（Identity as a Service）」のようなサービスも利用されるようになっています。しかし、企業などの組織ネットワークにおける認証サービスの中心は、今もなお「Active Directory」や「Red Hat Directory Server」といったLDAP（Lightweight Directory Access Protocol）サーバではないでしょうか。',0,'test','2023-06-03 15:30:46',NULL,NULL,'0'),
(null,'外部ユーザーストレージに連携してみよう③','LDAPサーバは、ユーザーだけでなく、組織内のコンピュータやその設定も一元管理でき、連携している組織内のシステムも多いため、今後も活用されていくことが予想されます。',0,'test','2023-06-03 16:30:46',NULL,NULL,'0'),
(null,'外部ユーザーストレージに連携してみよう④','Keycloakは、これらのLDAPサーバに保存されているユーザーを簡単に認証に利用できます。これを実現するのが「ユーザーストレージ連携」機能です。',0,'test','2023-06-03 17:30:46',NULL,NULL,'0'),
(null,'外部ユーザーストレージに連携してみよう⑤','ユーザーストレージ連携機能とは？\r　Keycloakのユーザーストレージ連携機能とは、LDAPなどの外部ユーザーストレージと連携するための機能です。これにより、外部ユーザーストレージに保存されているユーザーを使用して、Keycloakと連携しているアプリケーションにシングルサインオンできるようになります（図1）。',0,'test','2023-06-03 18:30:46',NULL,NULL,'0'),
(null,'外部ユーザーストレージに連携してみよう⑥','現在Keycloakでは、外部ユーザーストレージとしてActive DirectoryやRed Hat Directory ServerなどのLDAPサーバをデフォルトでサポートしています。さらに、「ユーザーストレージSPI（サービスプロバイダーインタフェース）」というインタフェースを実装して、上記以外のユーザーストレージ（RDBMSなど）と連携することもできます。Active DirectoryやRed Hat Directory Serverとの連携も、このユーザーストレージSPIを実装したもので実現しています。',0,'test','2023-06-04 08:30:46',NULL,NULL,'0'),
(null,'外部ユーザーストレージに連携してみよう⑦','●ユーザーストレージ連携の仕組み\r　Keycloakには内部ユーザーストレージとして、「H2」というRDBMSが組み込まれています。デフォルト設定のままであれば、H2でユーザーのデータが管理されることになります。通常は、「MySQL」などのRDBMSを使用するように設定を変更します。さらに、設定を追加することで、複数の異なる外部ユーザーストレージとも連携することもできます。',0,'test','2023-06-04 08:34:46',NULL,NULL,'0'),
(null,'リバースプロキシ型構成を構築してみよう①','本連載では、近年注目されている認証プロトコル「OpenID Connect」をサポートするオープンソースのシングルサインオンソフトウェア「Keycloak」の活用方法を解説していきます。Keycloakと標準的な認証／認可プロトコルに対応したプロキシを組み合わせることで、プロトコル未対応のアプリケーションに対し、安全なシングルサインオン環境を提供できます。',0,'test','2023-06-04 09:30:46',NULL,NULL,'0'),
(null,'リバースプロキシ型構成を構築してみよう②','はじめに\r　企業内には、以下のような理由で標準的な認証・認可プロトコルに対応できないケースが存在することもあると思います。',0,'test','2023-06-04 11:30:46',NULL,NULL,'0'),
(null,'リバースプロキシ型構成を構築してみよう③','リバースプロキシ型構成の構築\r　今回は、認証／認可プロトコル対応プロキシに「mod_auth_openidc」を使用した、Keycloakのリバースプロキシ型構成を構築していきます。また、スムーズに構築するため「Docker Compose」を利用します。これらは、必要な設定も含めて自動的に構築されるので、動くモノをベースに、Keycloakのリバースプロキシ型構成の理解を深めていただければと思います。',0,'test','2023-06-04 14:30:46',NULL,NULL,'0'),
(null,'リバースプロキシ型構成を構築してみよう④','前提条件\r　実行環境（OS）は問いませんが、「Docker CE」（※1）および「Docker Compose」（※2）がインストールされていることが、前提条件となります。',0,'test','2023-06-05 07:30:46',NULL,NULL,'0'),
(null,'リバースプロキシ型構成を構築してみよう⑤','既に、Dockerの実行環境をお持ちの方は、この部分は読み飛ばして構いません。お持ちでない方は、以下のサイトを参考にインストールしてください。',0,'test','2023-06-05 08:30:46',NULL,NULL,'0'),
(null,'リバースプロキシ型構成を構築してみよう⑥','Dockerは、さまざまなOSに対応しているので、任意の環境にインストールしてください。\rまた、必要に応じて、DockerのHTTP/HTTPSプロキシの通し方について記載された「HTTP/HTTPS proxy」（https://docs.docker.com/engine/admin/systemd/#/http-proxy）を参照し、設定を行ってください。',0,'test','2023-06-05 09:30:46',NULL,NULL,'0'),
(null,'リバースプロキシ型構成を構築してみよう⑦','　なお、筆者はCentOS 7で確認を行ったため、これ以降CentOS 7をベースにして記載しますが、読者の実行環境に読み替えて実施することも可能です。',0,'test','2023-06-05 10:30:46',NULL,NULL,'0'),
(null,'Keycloakで認可サービスを試してみよう［前編］①','本連載では、近年注目されている認証プロトコル「OpenID Connect」をサポートするオープンソースのシングルサインオン（SSO）ソフトウェア「Keycloak」の活用方法を解説していきます。Keycloakの認可サービスを利用することで、アプリケーションに対して、より細やかで柔軟なアクセス制御を実現することが可能です。前編、後編の2回に分けて、その方法を解説します。',0,'test','2023-06-05 11:30:46',NULL,NULL,'0'),
(null,'Keycloakで認可サービスを試してみよう［前編］②','はじめに\r　通常、Webアプリケーションではユーザーの権限に応じてなんらかのアクセス制御を行います。本連載第7回「Keycloakで実用的なリバースプロキシ型構成を構築してみよう」では、ユーザーが管理者のロールを持っているかどうかによって、管理者用のパスのアクセス制御を行う例がありました。',0,'test','2023-06-05 12:30:46',NULL,NULL,'0'),
(null,'Keycloakで認可サービスを試してみよう［前編］③','このようなシンプルなアクセス制御であれば、リバースプロキシ型でも実現可能ですが、アプリケーションに複雑なアクセス制御の要件があると実現が難しい場合があります。',0,'test','2023-06-05 13:30:46',NULL,NULL,'0'),
(null,'Keycloakで認可サービスを試してみよう［前編］④','OpenID Connect用のクライントアダプターのうち、Javaアダプターでは以下のミドルウェア用のモジュールが提供されているため、これらのミドルウェアで動作しているアプリケーションに対して、認可サービスが利用可能です。',0,'test','2023-06-05 14:30:46',NULL,NULL,'0'),
(null,'KeycloakのWebAuthn Passwordlessについて①','きっかけ\rKeycloak 8.0 にWebAuthnがサポートされるようになりました。\rさらにKeycloak 9.0 ではWebAuthnのパスワードレス認証ができるようになりました。 \rKeycloakもWebAuthn対応が充実してきたし、Authenticatorの登録及びAuthenticatorを使った認証を試してみようと思いました。',0,'test','2023-06-05 20:30:43',NULL,NULL,'0'),
(null,'KeycloakのWebAuthn Passwordlessについて②','やってみたこと\rほぼドキュメント記載のことをつらつら実施\rWebAuthnポリシーの設定\rWebAuthnフローの作成\rAuthenticatorの登録と認証\rCredintialsのデータ保存確認',0,'test','2023-06-05 22:30:43',NULL,NULL,'0')

DROP TABLE IF EXISTS t_comment;
CREATE TABLE `t_comment` (
  `comment_no` int NOT NULL AUTO_INCREMENT,
  `board_id` int NOT NULL,
  `comment_content` varchar(100) DEFAULT NULL,
  `register_user_id` varchar(18) DEFAULT NULL,
  `register_date` timestamp NULL DEFAULT NULL,
  `update_user_id` varchar(18) DEFAULT NULL,
  `update_date` timestamp NULL DEFAULT NULL,
  `delete_flag` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`comment_no`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS t_user;
CREATE TABLE `t_user` (
  `user_id` varchar(18) NOT NULL,
  `user_pass` varchar(100) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `user_birthday` varchar(8) NOT NULL,
  `user_gender` varchar(2) NOT NULL,
  `user_mail` varchar(50) NOT NULL,
  `join_reason` varchar(2) NOT NULL,
  `register_user_id` varchar(18) DEFAULT NULL,
  `register_date` timestamp DEFAULT NULL,
  `update_user_id` varchar(18) DEFAULT NULL,
  `update_date` timestamp DEFAULT NULL,
  `delete_flg` varchar(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `t_user` VALUES ('testid','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','ipsg','20000101','01','test@test.com','01','SYSTEM','2023-06-01 7:00:00',NULL,NULL,'0');
