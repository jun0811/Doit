<template>
  <div>
    <v-card
      class="mx-auto"
    >
      <v-virtual-scroll
        :bench="benched"
        :items="items"
        height="600"
        item-height="64"
      >
        <template v-slot:default="{ item }">
          <v-list-item :key="item">
            <v-list-item-action>
              <v-btn
                fab
                small
                depressed
                class="joined-group-btn"
              >
              {{ item }}
              </v-btn>
            </v-list-item-action>

            <v-list-item-content>
              <v-list-item-title>
                Group {{ item }}
              </v-list-item-title>
            </v-list-item-content>


          </v-list-item>

          <v-divider></v-divider>
        </template>
      </v-virtual-scroll>
    </v-card>
  </div>
</template>

<script>
  export default {
    data: () => ({
      benched: 0,
    }),
    computed: {
      items () {
        return Array.from({ length: this.length }, (k, v) => v + 1)
      },
      length () {
        return 20
      },
    },
  }
</script>

<style>

</style>