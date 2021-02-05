<template>
  <v-container class="pa-3 pa-sm-16"> 
    <v-row class="mx-3 mx-sm-16 mb-5">
      <h4>카테고리</h4>
    </v-row>
    <v-card class="mx-auto mx-3 mx-sm-16">
      <v-row class="py-10 px-5">
        <v-col cols="2">
          <li class="category-icon"><font-awesome-icon class="category-icon" icon="guitar"/></li>
        </v-col>
        <v-col cols="2">
          <li class="category-icon"><font-awesome-icon class="category-icon" icon="book"/></li>
        </v-col>
        <v-col cols="2">
          <li class="category-icon"><font-awesome-icon class="category-icon" icon="laptop-code"/></li>
        </v-col>
        <v-col cols="2">
          <li class="category-icon"><font-awesome-icon class="category-icon" icon="running"/></li>
        </v-col>
        <v-col cols="2" class="category-icon">
          <li class="category-icon"><font-awesome-icon class="category-icon" icon="carrot"/></li>
        </v-col>
        <v-col cols="2">
          <li class="category-icon"><font-awesome-icon class="category-icon" icon="spell-check"/></li>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
export default {

}
</script>

<style scoped>

.category-icon {
  list-style: none;
  display: inline-block;
  width: 50px;
  height: 50px;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .category-icon {
    list-style: none;
    display: inline-block;
    width: 30px;
    height: 30px;
  }
}

</style>