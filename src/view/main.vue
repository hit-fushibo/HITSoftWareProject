<template>
    <div class="container">
        <img :src="currentImage" alt="wife image" class="image" />
    </div>
</template>

<script>
import wife1 from '@/assets/wifes/108263077_p0.png'
import wife2 from '@/assets/wifes/111421592_p0.png'
import wife3 from '@/assets/wifes/崩落华.jpg'
import wife4 from "@/assets/wifes/符华人为崩落.jpg"
import wife5 from "@/assets/wifes/符华生日会.jpg"
import wife6 from "@/assets/wifes/上仙1.png"
import wife7 from "@/assets/wifes/上仙2.jpg"
import wife8 from "@/assets/wifes/上仙小识.png"
import wife9 from "@/assets/wifes/月轮照夕阳.jpg"

export default {
    data() {
        return {
            images: [
                wife1,
                wife2,
                wife3,
                wife4,
                wife5,
                wife6,
                wife7,
                wife8,
                wife9
                // 添加更多图片路径
            ],
            currentImageIndex: 0,
        };
    },
    computed: {
        currentImage() {
            return this.images[this.currentImageIndex];
        },
    },
    mounted() {
        this.intervalId = setInterval(() => {
            this.currentImageIndex = Math.floor(Math.random() * this.images.length);
        }, 5000);
    },
    beforeDestroy() {
        clearInterval(this.intervalId);
    },
};
</script>

<style scoped>
.container {
    height: 100vh;
    width: 100vw;
    display: flex;
    justify-content: center;
    align-items: center;
}

.image {
    max-width: 100%;
    max-height: 100%;
}
</style>