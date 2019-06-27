<template>
	<div>
		<el-button @click="sendWebSocket">send</el-button>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				msg:''
			}
		},
		computed:{
			getWebsocketMsg() {
				console.log("computed getWebsocketMsg")
				return this.$store.state.wsMsg
			}
		},
		watch: {
			getWebsocketMsg(old,newVal) {
				console.log("wsmsg:"+newVal)
			}
		},
		methods: {
			sendWebSocket() {
				this.$store.dispatch('SockjsSend',JSON.stringify({'name': 'tongchen'}))
			}
		}
	}
</script>