<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                Number of feeds: {{numberOfFeeds}}
            </div>
        </div>
        <div class="row">
            <div class="col">
                Number of streams: {{numberOfStreams}}
            </div>
        </div>
        <div class="row">
            <div class="col">
                The API is available on this url: <span class="font-weight-bold">{{ apiUrl() }}</span>
            </div>
        </div>
    </div>
</template>

<script>
  import store from "../store";
  import {FETCH_STATUS} from '../store/actions.type';
  import {mapGetters} from 'vuex';

  export default {
    name: "Index",
    computed: {
      ...mapGetters(["numberOfFeeds", "numberOfStreams"])
    },
    mounted() {
      this.fetchStatus();
    },
    methods: {
      fetchStatus() {
        store.dispatch(FETCH_STATUS);
      },
      apiUrl() {
        return window.location + 'api';
      }
    }
  }
</script>

<style scoped>

</style>