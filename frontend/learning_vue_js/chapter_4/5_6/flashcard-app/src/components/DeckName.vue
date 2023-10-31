<template>
    <h1>{{ name }}</h1>
</template>

<script>
    export default {
        name: "DeckName",
        // This is just one of multiple ways to declare props
        props: ["name"]
    }
</script>

<!-- By adding `scoped`, style definitions declared here only apply to this component -->
<style scoped>
    h1 {
        font-style: italic;
    }
</style>