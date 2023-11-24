<script setup></script>

<template>
  <div class="footer">
    <div class="sections">
      <div class="menu">
        <h5 class="menuTitle">Enjoy Trip 소개</h5>
        <div class="footerContent">Enjoy Trip 소개</div>
        <div class="footerContent">문의하기</div>
        <div class="footerContent">사이트 이용 가이드</div>
        <div class="footerContent">사이트 맵</div>
      </div>
      <div class="menu">
        <h5 class="menuTitle">주요 내용</h5>
        <div class="footerContent">공지사항</div>
        <div class="footerContent">FAQ</div>
        <div class="footerContent">사이트 버그 제보</div>
      </div>
      <div class="menu">
        <h5 class="menuTitle">도움말</h5>
        <div class="footerContent">이용 약관</div>
        <div class="footerContent">개인정보 처리방침</div>
        <div class="footerContent">리소스 및 정책</div>
      </div>
      <div class="menu"></div>
    </div>
    <div class="copyright">
      <span style="color: #4e4e4e">© 2023. WildCard. All rights reserved.</span>
    </div>
  </div>
</template>

<style scoped>
@font-face {
  font-family: "NanumSquare";
  src: url("../../assets/fonts/NanumSquareR.ttf") format("truetype");
}
@font-face {
  font-family: "NanumSquareB";
  src: url("../../assets/fonts/NanumSquareB.ttf") format("truetype");
}
* {
  margin: 0;
  padding: 0;
  font-family: "NanumSquare";
}
.footer {
  width: 100%;
  height: 350px;
  background-color: rgb(240, 237, 230);
  display: flex;
  flex-direction: column;
}
.sections {
  margin: 0 0 0 150px;
  width: 70%;
  height: 300px;
  display: inherit;
  justify-content: space-around;
  flex-wrap: wrap;
  text-align: center;
  color: #292929;
}
.menu {
  display: inline-block;
  width: 250px;
  height: 200px;
  margin: 50px 0;
  text-align: start;
  font-size: 15px;
}
.menuTitle {
  margin-bottom: 2px;
}
.footerContent {
  text-align: start;
  margin: 15px 0 0 0;
}
.copyright {
  width: 400px;
  height: 100px;
  margin: 0 190px;
}
</style>
