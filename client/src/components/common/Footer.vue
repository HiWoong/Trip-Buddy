<script setup></script>

<template>
  <div class="footer">
    <div class="sections">
      <div class="menu"></div>
      <div class="menu"></div>
      <div class="menu"></div>
      <div class="menu"></div>
    </div>
  </div>
</template>

<style scoped>
.footer {
  width: 100%;
  height: 400px;
  background-color: antiquewhite;
  display: flex;
}
.sections {
  margin: 0 auto;
  background-color: silver;
  width: 70%;
  height: 400px;
  display: inherit;
  justify-content: space-around;
  flex-wrap: wrap;
  text-align: center;
}
.menu {
  display: inline-block;
  width: 250px;
  height: 300px;
  background-color: cadetblue;
}
</style>
