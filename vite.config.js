import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server:{
    host:'0.0.0.0',
    // remote
    // port:'8080',
    // local
    port:'5631',
    open:false,
    proxy:{
      '/api':{
        target:"http://localhost:9090",
        changeOrigin: true,
        rewrite: (path)=>path.replace(/^\/api/,'')
      }
    }
  }
})


