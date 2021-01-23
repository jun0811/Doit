<template>
  <v-expansion-panels class="px-2 py-2 px-sm-16" >
    <v-expansion-panel class="panel">
      <v-expansion-panel-header>
        <template v-slot>
          <v-row no-gutters>
            <v-col cols="4">
              <div class="group-image">
                <img src="" alt="">
              </div>
            </v-col>
            <v-col
              cols="8"
              class="text--secondary"
            >
              <span></span>
              <v-fade-transition leave-absolute>

              </v-fade-transition>
            </v-col>
          </v-row>
        </template>
      </v-expansion-panel-header>
      <v-expansion-panel-content>
        <v-row no-gutters>
          <v-spacer></v-spacer>
          <v-col cols="5">
          </v-col>

          <v-divider
            vertical
            class="mx-4"
          ></v-divider>

          <v-col cols="3">
            Select your destination of choice
            <br>
            <a href="#">Learn more</a>
          </v-col>
        </v-row>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            text
            color="secondary"
          >
            Cancel
          </v-btn>
          <v-btn
            text
            color="primary"
          >
            Save
          </v-btn>
        </v-card-actions>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script>
export default {
    data: () => ({
      date: null,
      trip: {
        name: '',
        location: null,
        start: null,
        end: null,
      },
    }),
}
</script>

<style scoped>
    .group-image {
        width: 100px;
        height: 100px;
        border-radius: 70%;
        overflow: hidden;
        background-color: #ffffbb
    }
    .panel {
    }
</style>