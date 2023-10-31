<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <DeckName name="Vue.js Fundamentals" />
</template>

<script>
import DeckName from './components/DeckName.vue';

export default {
  name: 'App',
  // Locally registers component as a child of the
  // current component as opposed to globally registering
  // components with `.component()` in the other examples
  // from this chapter
  components: {
    DeckName
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
