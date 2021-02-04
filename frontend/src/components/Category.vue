<template>
  <div>
    <v-row class="mx-3 mx-sm-16">
      Category
    </v-row>
    <v-row class="py-10">
      <v-col class="mx-3 mx-sm-16 temp-box d-flex align-center justify-space-around">
        <li class="category-icon"><font-awesome-icon class="fa-5x" icon="guitar"/></li>
        <li class="category-icon"><font-awesome-icon class="fa-5x" icon="book"/></li>
        <li class="category-icon"><font-awesome-icon class="fa-5x" icon="laptop-code"/></li>
        <li class="category-icon"><font-awesome-icon class="fa-5x" icon="running"/></li>
        <li class="category-icon"><font-awesome-icon class="fa-5x" icon="carrot"/></li>
        <li class="category-icon"><font-awesome-icon class="fa-5x" icon="spell-check"/></li>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>

.category-icon {
  list-style: none;
  display: inline-block;
}

</style>