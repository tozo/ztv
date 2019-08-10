<template>
    <div id="app">
        <div class="container-fluid">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" href="#">ZTV</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                            <router-link class="nav-link" to="/" :class="{'active': $route.fullPath ==='/'}">Home</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link class="nav-link" exact to="/settings" :class="{'active': $route.fullPath ==='/settings'}">Settings</router-link>
                        </li>
                    </ul>
                </div>
            </nav>
            <router-view class="view"></router-view>
        </div>
    </div>
</template>

<script>

  export default {
    name: 'app',
    components: {
    }
  }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        /*text-align: center;*/
        /*color: #2c3e50;*/
        /*margin-top: 60px;*/
    }
</style>
