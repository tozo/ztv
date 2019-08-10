<template>
    <div class="container-fluid">
        <div>
            <b-tabs content-class="mt-3">
                <b-tab title="Feeds" active>
                    <div class="row mb-2">
                        <div class="col-sm-9 col-xl-10 font-weight-bold">
                            Current feeds
                        </div>
                        <div class="col-sm-3 col-xl-2 text-right">
                            <button @click="submitUrl = true;$bvModal.show('modal-feed')" class="btn btn-primary">Add new feed</button>
                        </div>
                    </div>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Name</th>
                            <th scope="col">Url</th>
                            <th scope="col">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(feed, index) in feeds" :key="index">
                            <th scope="row">{{index + 1}}</th>
                            <td>{{feed.name}}</td>
                            <td>{{feed.url}}</td>
                            <td><a href="#" @click="deleteFeed(feed.id)">Delete</a></td>
                        </tr>
                        </tbody>
                    </table>

                    <b-modal id="modal-feed" title="Add Feed" class="container-fluid" @ok="addFeed()">
                        <font-awesome-icon :icon="faExchangeAlt" pull="right" size="lg" :title="submitUrl ? 'Upload file' : 'Submit url'" @click="switchForms()"/>
                        <form id="submit-url" v-if="submitUrl">
                            <div class="form-group">
                                <label for="name">Name</label>
                                <input v-model="feedForm.name" type="text" class="form-control" id="name"
                                       placeholder="Enter name">
                            </div>
                            <div class="form-group">
                                <label for="url">Url</label>
                                <input v-model="feedForm.url" type="text" class="form-control" id="url"
                                       placeholder="Url">
                            </div>
                        </form>
                        <form id="upload-file" v-if="!submitUrl">
                            <div class="form-group">
                                <label for="file-name">Name</label>
                                <input v-model="feedForm.name" type="text" class="form-control" id="file-name"
                                       placeholder="Enter name">
                            </div>
                            <div class="form-group">
                                <label for="file">File</label>
                                <input v-on:change="fileUpload($event.target.files)" type="file" class="form-control-file" id="file">
                            </div>
                        </form>
                    </b-modal>
                </b-tab>
                <b-tab title="Stream">
                    <div class="row mb-2">
                        <div class="col-sm-12 font-weight-bold">
                            Current streams
                        </div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Feed name</th>
                                <th scope="col">Name</th>
                                <th scope="col">Url</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="(stream, index) in streams" :key="index">
                                <th scope="row">{{index + 1}}</th>
                                <td>{{stream.feedName}}</td>
                                <td>{{stream.title}}</td>
                                <td>{{stream.url}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </b-tab>
                <b-tab title="Filter" @click="fetchConfigs()">
                    <div class="row mb-2">
                        <div class="col-sm-12 font-weight-bold">
                            Filtering streams
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            Total number of streams: <span class="font-weight-bold">{{numberOfStreams}}</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            Number of filtered streams: <span class="font-weight-bold">{{numberOfFilteredStreams}}</span>
                        </div>
                    </div>
                    <form class="mt-3">
                        <div class="row">
                            <div class="col-5">
                                <div class="form-group">
                                    <label for="filter">Filters, if you want multiple of them, separate them by semicolon (;)</label>
                                    <input v-model="filterValue" type="text" class="form-control" id="filter" placeholder="Enter filters">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-5">
                                <div class="float-right">
                                    <button type="button" class="btn btn-primary" @click="addConfig()">Save</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </b-tab>
            </b-tabs>
        </div>
    </div>
</template>

<script>
  import store from "../store";
  import {ADD_CONFIG, ADD_FEED, DELETE_FEED, FETCH_CONFIG, FETCH_FEEDS, FETCH_STATUS, FETCH_STREAMS} from '../store/actions.type';
  import {mapGetters} from 'vuex';
  import {faExchangeAlt} from '@fortawesome/free-solid-svg-icons'
  import {SET_CONFIG_VALUE} from '../store/mutations.type';

  export default {
    name: "Settings",
    components: {},
    data() {
      return {
        feedForm: {
          name: "",
          url: "",
          file: ""
        },
        faExchangeAlt: faExchangeAlt,
        submitUrl: true,
        filter: {}
      }
    },
    computed: {
      ...mapGetters(["feeds", "streams", "numberOfStreams", "numberOfFilteredStreams"]),
      filterValue: {
        get() {
          return this.filter.value;
        },
        set(value) {
          store.commit(SET_CONFIG_VALUE, value);
        }
      }
    },
    mounted() {
      this.fetchFeeds();
      this.fetchStreams();
      this.fetchStatus();
    },
    methods: {
      fetchFeeds() {
        store.dispatch(FETCH_FEEDS);
      },
      fetchStreams() {
        store.dispatch(FETCH_STREAMS);
      },
      fetchConfigs() {
        store.dispatch(FETCH_CONFIG, "filter")
          .then(() => {
            this.filter = store.getters.getConfig;
          });
      },
      fetchStatus() {
        store.dispatch(FETCH_STATUS);
      },
      addFeed() {
        store.dispatch(ADD_FEED, this.feedForm).then(() => {
          this.feedForm.name = "";
          this.feedForm.url = "";
          this.feedForm.file = "";
          this.fetchFeeds();
          this.fetchStreams();
        }).catch(({response}) => {
          this.feedForm.name = "";
          this.feedForm.url = "";
          this.feedForm.file = "";
          let error = response.data.message;

          this.displayError(error);
        });
      },
      deleteFeed(id) {
        store.dispatch(DELETE_FEED, id).then(() => {
          this.fetchFeeds();
          this.fetchStreams();
        }).catch(({response}) => {
          let error = response.data.message;

          this.displayError(error);
        });
      },
      fileUpload(fileList) {
        this.feedForm.file = fileList[0];
      },
      switchForms() {
        this.submitUrl = !this.submitUrl;
      },
      addConfig() {
        let config = {
          "name": "filter",
          "value": this.filter.value
        };
        store.dispatch(ADD_CONFIG, config).then(() => {
            this.displaySuccess("Config successfully saved");
            this.fetchStatus();
        }).catch(({response}) => {
          let error = response.data.message;

          this.displayError(error);
        });
      },
      displayError(message) {
        this.$bvToast.toast(message, {
          title: "Error",
          variant: "danger",
          autoHideDelay: 5000
        })
      },
      displaySuccess(message) {
        this.$bvToast.toast(message, {
          title: "Success",
          variant: "success",
          autoHideDelay: 5000
        })
      }
    }
  }
</script>

<style scoped>

</style>