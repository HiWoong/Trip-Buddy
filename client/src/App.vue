<script setup>
import { useRouter, RouterView } from "vue-router";
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
</script>

<template>
  <header>
    <Header />
  </header>
  <body>
    <router-view></router-view>
  </body>
  <footer>
    <Footer />
  </footer>
</template>

<style>
header {
  z-index: 2;
  position: relative;
}
body {
  z-index: 1;
  position: relative;
}
</style>
