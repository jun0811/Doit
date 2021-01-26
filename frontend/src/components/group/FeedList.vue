<template>
  <div>
    <v-card
      class="mx-auto"
    >
      <v-virtual-scroll
        :bench="benched"
        :items="items"
        height="600"
        item-height="400"
      >
        <template v-slot:default="{ item }">
          <v-list-item :key="item" class="pa-0">
            <FeedCard></FeedCard>
          </v-list-item>
        </template>
        
      </v-virtual-scroll>
    </v-card>
  </div>
</template>

<script>
import FeedCard from '@/components/group/FeedCard.vue'

  export default {
    components: {
      FeedCard,
    },
    data: () => ({
      benched: 0,
    }),
    computed: {
      items () {
        return Array.from({ length: this.length }, (k, v) => v + 1)
      },
      length () {
        return 5
      },
    },
  }
</script>

<style>

</style>
