<template>
  <div ref="trigger"></div>
</template>
<script setup>
import { onMounted, ref } from "vue";
const trigger = ref();
const options = {
  root: null,
  threshold: 1,
};
const emit = defineEmits(["triggerIntersected"]);
let observer = null;
const handleIntersect = (entry) => {
  if (entry.isIntersecting) emit("triggerIntersected", trigger.value);
};

onMounted(() => {
  try {
    observer = new IntersectionObserver((entries) => {
      handleIntersect(entries[0]);
    }, options);
    observer.observe(trigger.value);
  } catch (err) {
    console.error(err);
  }
});
</script>
