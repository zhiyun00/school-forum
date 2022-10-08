<template>
  <div>
    <div id="nav">
      <span style="font-size: 36px;" @click="this.$router.push({name:'/'})">
        <b>
          <span style="color: #4286F3;">W</span>
          <span style="color: #FAC230;">H</span>
          <span style="color: #55AF7B;">U</span>
        </b>
      </span>
    </div>
    <router-view/>
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding-top: 50px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
