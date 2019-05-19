<template>
  <div>
    <div v-if="error != null" class="alert alert-danger" role="alert">
       <h4 class="alert-heading">🗿Shucks!</h4>
      <p>{{ error.errorMessage }}</p>
      <b-button v-b-toggle.collapse-1 variant="outline-danger">More</b-button>
      <b-collapse id="collapse-1" class="mt-2">
        <p> {{ error.stackTrace }} </p>
      </b-collapse>
    </div>

    <div v-if="fcState == null">
      <h1>Loading...</h1>
    </div>
    <div v-if="fcState != null">
      <h1>Fadecandy Server Status</h1>
      <b-card>
        <b-button
            class="d-block mt-2"
            v-if="fcState.isRunning == false"
            v-on:click="fcStart"
            variant="outline-success">
          TURN ON
        </b-button>
        <b-button
            class="d-block mt-2"
            v-if="fcState.isRunning == true"
            v-on:click="fcStop"
            variant="outline-danger">
          TURN OFF
        </b-button>
        <b-button
            class="d-block mt-2"
            v-on:click="fcRestart"
            variant="outline-warning">
          RESTART
        </b-button>

        <b-list-group
            class="mt-4">
          <div v-if="fcState.connectedDevices.length == 0">⚡️ No Connected Devices</div>
          <b-list-group-item
              class="flex-column align-items-start"
              v-for="item in fcState.connectedDevices"
              v-bind:key="item.serial">
            <h5>{{item.type}}</h5>
            <p class="d-inline-block">Serial number:&nbsp;</p><p class="d-inline-block text-monospace">{{item.serial}}</p>
            <small class="d-block">Firmware version: {{item.version}}</small>
          </b-list-group-item>
        </b-list-group>
      </b-card>
      <b-card
          class="mt-4">
        <b-button
            class="d-inline"
            v-on:click="fcRestart"
            variant="outline-success">
          UPDATE
        </b-button>
        <b-button
            class="d-inline ml-2"
            v-on:click="fcRestart"
            variant="outline-secondary">
          RESET
        </b-button>
        <codemirror
            class="mt-4"
            :value="fcConfigString"
            :options="cmOptions">
        </codemirror>
      </b-card>
    </div>
    <b-card
        class="mt-4 mb-4">
      <p>© Simonas Sankauskas, 2019</p>
      <b-link href="https://github.com/simonassank/fadecandy-rest">Source Code</b-link>
    </b-card>
  </div>
</template>

<script>
import axios from 'axios';

export const HTTP = axios.create({
  baseURL: `http://localhost:8080/`,
})

export default {
  data() {
    return {
      cmOptions: {
        tabSize: 4,
        mode: 'text/javascript',
        lineNumbers: true,
        line: true,
        highlightDifferences: true,
        theme: "monokai",
      }
    }
  },
  name: 'HelloWorld',
  props: {
    fcState: Object,
    fcConfig: Object,
    fcConfigString: String,
    error: Object,
  },
  created: function () {
    setInterval(() => {
      this.fcGetState()
      this.fcGetConfig()
    }, 1000)
  },
  methods: {
    updateState: function() {
      this.fcGetState()
    },
    fcGetState: function() {
      HTTP.get(`fc`)
      .then(response => {
        this.fcState = response.data.value
      })
      .catch(e => {
        this.error = e.response.data
      })
    },
    fcStart: function() {
      HTTP.get(`/fc/start`)
      .then(response => {
        this.msg = JSON.stringify(response.data)
        this.updateState()
      })
      .catch(e => {
        this.error = e.response.data
      })
    },
    fcStop: function() {
      HTTP.get(`fc/stop`)
      .then(response => {
        this.msg = JSON.stringify(response.data)
        this.updateState()
      })
      .catch(e => {
        this.error = e.response.data
      })
    },
    fcRestart: function() {
      HTTP.get(`fc/restart`)
      .then(response => {
        this.msg = JSON.stringify(response.data)
        this.updateState()
      })
      .catch(e => {
        this.error = e.response.data
      })
    },
    fcGetConfig: function() {
      HTTP.get(`fc/config`)
      .then(response => {
        this.fcConfig = response.data.value
        this.fcConfigString = JSON.stringify(this.fcConfig, null, 2)
      })
      .catch(e => {
        this.error = e.response.data
      })
    },
    fcUpdateConfig: function() {
      HTTP.post(`fc/config`, {
        headers: {
          'accept': 'application/json'
        },
        body: this.fcConfigString
      })
      .then(response => {
        this.fcConfig = response.data.value
      })
      .catch(e => {
        this.error = e.response.data
      })
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
