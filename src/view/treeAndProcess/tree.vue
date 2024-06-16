<script setup>
import { ref, onMounted } from 'vue'
import * as d3 from "d3";
import avatar from '@/assets/logo.svg'

let selectedResult = -1;
let searchKeyword = ref('');
let me = ref([]);
let teachers = ref([]);
let students = ref([]);
let nodes = ref([]);
let links = ref([]);
let results = ref([]);
let selectNodeType=ref(0);//0老师，1学生
let selectNodeId=ref("");


results.value = [
    { img: '', name: '张三', id: '987654321', email: 'zhangsan@example.com' },
    { img: '', name: '李四', id: '123456789', email: 'lisi@example.com' },
]

me.value = [{ name: '王五',id:"123456789",level:-1,startTime:"",endTime:"", type: 0 }];
teachers.value = [{ name: '李四',id:"123456789",level:0,startTime:"2015-05",endTime:"2017-06",type: 1 }];
students.value = [{ name: '张三',id:"123456789",level:1,startTime:"2020-02",endTime:"2023-06",type: 2 }];
nodes.value = [
    { name: '张三' },
    { name: '李四' },
    { name: '王五' },
];
links.value = [
    { source: '张三', target: '李四' },
    { source: '李四', target: '王五' },
];

const r = 30;
let name = '';

function cul(x1, y1, r1, x2, y2, r2) {

    let dx = x2 - x1;
    let dy = y2 - y1;
    let theta;
    if (dx === 0) {
        theta = dy >= 0 ? 90 : -90;
    } else {
        theta = Math.atan2(dy, dx);
    }
    let sx = x1 + r1 * Math.cos(theta);
    let sy = y1 + r1 * Math.sin(theta);

    dx = x1 - x2;
    dy = y1 - y2;
    if (dx === 0) {
        theta = dy >= 0 ? 90 : -90;
    } else {
        theta = Math.atan2(dy, dx);
    }
    dx = x2 + r2 * Math.cos(theta);
    dy = y2 + r2 * Math.sin(theta);

    return [sx, sy, dx, dy];

}
function drawChart(newStudents, newTeachers, me) {
    me.value = me
    teachers.value = newTeachers
    students.value = newStudents
    nodes.value = me.value.concat(students.value, teachers.value)
    let teacherLinks = teachers.value.map(teacher => {
        return { source: teacher.name, target: me.value[0].name };
    });
    let studentLinks = students.value.map(student => {
        return { source: me.value[0].name, target: student.name }
    });

    links.value = teacherLinks.concat(studentLinks);
    const container = document.getElementById('tree'); // 获取容器元素
    container.innerHTML = ''; // 清空容器中的所有子元素

    // 获取容器的宽度和高度
    const containerWidth = document.getElementById('tree').clientWidth;
    const containerHeight = document.getElementById('tree').clientHeight;

    const svg = d3.select('#tree')
        .append('svg')
        .attr('width', containerWidth)
        .attr('height', containerHeight);
    // 添加箭头标记的声明
    svg.append('defs')
        .append('marker')
        .attr('id', 'arrow')
        .attr('markerWidth', 8)
        .attr('markerHeight', 8)
        .attr('refX', 8)
        .attr('refY', 3)
        .attr('orient', 'auto')
        .attr('markerUnits', 'strokeWidth')
        .append('path')
        .attr('d', 'M0,0 L0,6 L9,3 z')
        .attr('fill', 'black');


    // 创建一个力导向图布局，设置范围为容器的宽度和高度


    const simulation = d3.forceSimulation(nodes.value)
        .force('link', d3.forceLink(links.value).id(d => d.name))
        .force('charge', d3.forceManyBody().strength(-500 * r))
        .force('center', d3.forceCenter(containerWidth / 2, containerHeight / 2));


    // 创建连接线
    const link = svg.selectAll('.link')
        .data(links.value)
        .enter()
        .append('line')
        .attr('class', 'link')
        .attr('stroke', 'black')
        .attr('stroke-width', 2)
        .attr('marker-end', 'url(#arrow)'); // 使用箭头标记

    // 创建节点
    const node = svg.selectAll('.node')
        .data(nodes.value)
        .enter()
        .append('circle')
        .attr('class', 'node')
        .attr('r', r)
        .attr('fill', d => { if (d.type == 0) return 'white'; else if (d.type == 1) return 'red'; else return 'blue'; }) // 设置节点填充色为空
        .attr('opacity', 0.5)
        .attr('stroke', 'black') // 设置节点边框颜色为黑色
        .attr('stroke-width', 2) // 设置节点边框宽度为2
        .style('pointer-events', 'all') // 设置鼠标事件在整个节点区域内生效
        .on('contextmenu', (event, d) => {
            if(d.type==0){return}
            name = d.name;
            console.log(name);
            event.preventDefault();

            // 显示菜单
            d3.select('.context-menu')
                .style('display', 'block')
                .style('left', event.pageX + 'px')
                .style('top', event.pageY + 'px');


        });
    //添加文字
    const texts = svg.selectAll("text")
        .data(nodes.value)
        .enter()
        .append("text")
        .style("fill", "black")
        .attr("dx", 0)
        .attr("dy", 0)
        .text(d => d.name);
    // 更新连接线的位置
    simulation.on('tick', () => {

        link
            .attr('x1', d => cul(d.source.x, d.source.y, r, d.target.x, d.target.y, r)[0])
            .attr('y1', d => cul(d.source.x, d.source.y, r, d.target.x, d.target.y, r)[1])
            .attr('x2', d => cul(d.source.x, d.source.y, r, d.target.x, d.target.y, r)[2])
            .attr('y2', d => cul(d.source.x, d.source.y, r, d.target.x, d.target.y, r)[3]);
        node
            .attr('cx', d => d.x)
            .attr('cy', d => d.y);
        texts.attr("x", d => d.x - 10).attr("y", d => d.y + 10);
    });


    // 添加鼠标点击和拖拽功能
    node.call(d3.drag()
        .on('start', (event, d) => {
            if (!event.active) simulation.alphaTarget(0.3).restart();
            d.fx = d.x;
            d.fy = d.y;
        })
        .on('drag', (event, d) => {
            d.fx = event.x;
            d.fy = event.y;
        })
        .on('end', (event, d) => {
            if (!event.active) simulation.alphaTarget(0);
            d.fx = null;
            d.fy = null;
        })
    );
}
const selectResult = (resultNumber) => {
    // 取消先前选中的结果样式
    if (selectedResult >= 0) {
        let element = document.querySelector('.result_box.selected');
        if (element) {
            // console.log('成功选择到元素:', element);
            element.classList.remove('selected')
        } else {
            console.log('1未能选择到元素');
        }

    }

    // 标记选中的结果
    selectedResult = resultNumber;
    console.log('选中的结果是：', selectedResult);

    // 添加选中结果的样式

    let elements = document.querySelectorAll('.result_box');
    let element = elements[resultNumber - 1];
    if (element) {
        // console.log('成功选择到元素:', element);
        element.classList.add('selected');
    } else {
        console.log('2未能选择到元素', elements);
    }

}

const showTree = (resultNumber) => {

    // 标记选中的结果
    selectedResult = resultNumber;
    console.log('选中的结果是：', selectedResult);
    // 获取选中的结果框
    const selected = document.querySelector('.result_box:nth-child(' + String(selectedResult) + ')');

    // 从结果框中获取 ID
    const idElement = selected.querySelector('.top-info p:nth-child(2)');
    const id = idElement.textContent.split('：')[1];
    console.log('展示 ID 为:', id, "的师承树");

    //取消一并引起的两次单击事件的设置，直接设置为没有选中
    if (selectedResult >= 0) {
        let element = document.querySelector('.result_box.selected');
        if (element) {
            element.classList.remove('selected')
        } else {
            console.log('3未能选择到元素');
        }

    }
    selectedResult = -1;
}

const search = () => {

}

const addStudent = () => {
    console.log(selectedResult)
    if (selectedResult < 0) {
        alert("请选择一个人");
        return;
    }
    // 获取选中的结果框
    const selected = document.querySelector('.result_box:nth-child(' + String(selectedResult) + ')');

    // 从结果框中获取 ID
    const idElement = selected.querySelector('.top-info p:nth-child(2)');
    const id = idElement.textContent.split('：')[1];

    console.log('添加学生的 ID 为:', id);

}

const addTeacher = () => {
    console.log(selectedResult)
    if (selectedResult < 0) {
        alert("请选择一个人");
        return;
    }
    // 获取选中的结果框
    const selected = document.querySelector('.result_box:nth-child(' + String(selectedResult) + ')');

    // 从结果框中获取 ID
    const idElement = selected.querySelector('.top-info p:nth-child(2)');
    const id = idElement.textContent.split('：')[1];

    console.log('添加老师的 ID 为:', id);

}
d3.select(document).on('click', () => {
    // 隐藏菜单栏
    d3.select('.context-menu').style('display', 'none');
});

onMounted(() => {
    drawChart(students.value, teachers.value, me.value);
});
</script>

<template>
    <div class="context-menu" style="display: none; position: absolute;">
        <div class="context-menu-item">修改</div>
        <div class="context-menu-item">删除</div>
    </div>

    <div id="TreeAndSearch">
        <div id="tree">

        </div>
        <div id="search">
            <div class="button-container">
                <el-input v-model="searchKeyword" placeholder="UID/手机号/姓名"></el-input>
                <el-button @click="search">搜索</el-button>
            </div>
            <div id="searchResult" class="search-result">
                <el-card v-for="(result, index) in results" :key="result.id" class="result_box"
                    @click="selectResult(index + 1)" @dblclick="showTree(index + 1)">
                    <div slot="header" class="avatar">
                        <!-- <img :src="result.img" alt="头像"> -->
                        <img v-if="result.img" :src="result.img" alt="头像" />
                        <img v-else :src="avatar" alt="头像" />
                    </div>
                    <div class="info">
                        <div class="top-info">
                            <p>姓名：{{ result.name }}</p>
                            <p>ID：{{ result.id }}</p>
                        </div>
                        <div class="bottom-info">
                            <p>邮箱：{{ result.email }}</p>
                        </div>
                    </div>
                </el-card>
            </div>
            <div class="button-container">
                <el-button class="add-student" @click="addStudent">添加为学生</el-button>
                <el-button class="add-teacher" @click="addTeacher">添加为老师</el-button>
            </div>
        </div>
    </div>
</template>

<style scoped>
#TreeAndSearch {
    display: flex;
    height: 100%;
    /* 占据父容器的全部高度 */
}

#tree {
    width: 60%;
    /* 第一个容器占据 60% 的宽度 */
    height: 100%;
    /* 占据整个容器的高度 */
}

#search {
    width: 40%;
    /* 第二个容器占据剩余的 40% 的宽度 */
    height: 100%;
    /* 占据整个容器的高度 */
    overflow: auto;
    /* 如果内容溢出，显示滚动条 */
}

.search-box {
    margin-bottom: 10px;
}

.search-result {
    border: 1px solid #ccc;
    padding: 5px;
    margin-bottom: 20px;
    height: 70%;
    overflow-y: auto;
}

.button-container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: flex-start;


}

.add-student,
.add-teacher {
    margin-top: 10px;
}

.result_box {
    display: flex;
    flex-direction: column;
    padding: 10px;
    background-color: #f0f0f0;
    border: 1px solid #ccc;
}

.result_box:hover {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    background-color: #e0e0e0;
}

.result_box.selected {
    background-color: #e0e0e0;
}

.avatar {
    flex: 1;
    margin-right: 10px;
}

.avatar img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
}

.info {
    flex: 3;
    display: flex;
    flex-direction: column;
}

.top-info,
.bottom-info {
    padding: 5px;
}

.top-info p,
.bottom-info p {
    margin: 5px 0;
}

.context-menu {
    position: absolute;
    border: 1px solid #ccc;
    background-color: #f0f0f0;
    padding: 0;
    /* 取消外边距 */
    box-sizing: border-box;
    /* 内边距不会增加元素的总宽度 */

    box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
}

.context-menu-item {
    padding: 8px 15px;
    cursor: pointer;
    transition: background-color 0.3s;
    border-bottom: 1px solid #ccc;
    /* 添加垂直分隔线 */
}

.context-menu-item:last-child {
    border-bottom: none;
    /* 最后一个菜单项去除分隔线 */
}

.context-menu-item:hover {
    background-color: #e0e0e0;
}
</style>