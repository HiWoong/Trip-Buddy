<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { RouterView } from "vue-router";
import http from "@/util/http-common.js";

const router = useRouter();

defineProps({
  articleNo: Number,
  userId: String,
  subject: String,
  content: String,
  hit: Number,
  registerTime: String,
});
</script>
<template>
  <tr class="text-center">
    <th scope="row">{{ articleNo }}</th>
    <td class="text-start">
      <RouterLink
        class="nav-link active"
        aria-current="page"
        :to="{ name: 'BoardDetail', params: { articleNo } }"
      >
        <a class="article-title link-dark" data-no="${{ articleNo }}" style="text-decoration: none">
          {{ subject }}
        </a>
      </RouterLink>
    </td>
    <td>{{ userId }}</td>
    <td>{{ hit }}</td>
    <td>{{ registerTime }}</td>
  </tr>
</template>
<style scoped></style>
