<script setup>
import { ref, onMounted, computed, reactive } from 'vue'
import * as d3 from "d3";
import avatar from '@/assets/logo.svg'
import {
    Check,
    Delete,
    Edit
} from '@element-plus/icons-vue'

let selectedResult = -1;

let addType = ref(0);//1-添加学生2-添加老师
let addName = ref("");//添加的姓名
let addUid = ref("");//添加的uid
let addToUid = ref("");//被添加的人的Uid
let addToName = ref("");//被添加人的姓名
let addTableVisible = ref(false);//添加弹窗可视化控制

const addFrom = reactive({
    level: '',
    startTime: '',
    endTime: ''
})
const addOptions = [
    {
        value: '0',
        label: '本科',
    },
    {
        value: '1',
        label: '硕士',
    },
    {
        value: '2',
        label: '博士',
    }
]

const handleStartDateChange = (value) => {
    if (value) {
        // 将日期转换为指定格式 YYYYMM
        const formattedDate = value.getFullYear() + '' + (value.getMonth() + 1).toString().padStart(2, '0');
        addFrom.startTime = formattedDate;
    } else {
        addFrom.startTime = '';
    }
}

const handleEndDateChange = (value) => {
    if (value) {
        // 将日期转换为指定格式 YYYYMM
        const formattedDate = value.getFullYear() + '' + (value.getMonth() + 1).toString().padStart(2, '0');
        addFrom.endTime = formattedDate;
    } else {
        addFrom.endTime = '';
    }
}

const handleStartDateChangeM = (value) => {
    if (value) {
        // 将日期转换为指定格式 YYYYMM
        const formattedDate = value.getFullYear() + '' + (value.getMonth() + 1).toString().padStart(2, '0');
        modifyFrom.startTime = formattedDate;
    } else {
        modifyFrom.startTime = '';
    }
}

const handleEndDateChangeM = (value) => {
    if (value) {
        // 将日期转换为指定格式 YYYYMM
        const formattedDate = value.getFullYear() + '' + (value.getMonth() + 1).toString().padStart(2, '0');
        modifyFrom.endTime = formattedDate;
    } else {
        modifyFrom.endTime = '';
    }
}


let modifyName = ref("");//编辑的姓名
let modifyUid = ref("");//编辑的uid
let modifyToUid = ref("");//被编辑的人的Uid
let modifyToName = ref("");//被编辑人的姓名
let modifyRel = ref([]);//被编辑人与编辑人的师生关系
let modifyTableVisible = ref(false);//编辑弹窗可视化控制
let inModifyTableVisible = ref(false)
const modifyFrom = reactive({
    level: '',
    startTime: '',
    endTime: ''
})

let delType = ref(0);//1-删除学生2-删除老师
let delName = ref("");//删除的姓名
let delUid = ref("");//删除的uid
let delToUid = ref("");//被删除的人的Uid
let delToName = ref("");//被删除人的姓名
let delTableVisible = ref(false);//删除弹窗可视化控制


const years = computed(() => {
    const currentYear = new Date().getFullYear();
    return Array.from({ length: currentYear - 1949 }, (_, index) => (currentYear - index).toString());
})

const months = computed(() => {
    return Array.from({ length: 12 }, (_, index) => (index + 1).toString().padStart(2, '0'));
})

const addTitle = computed(() => {
    if (addType.value === 1) {
        return "添加学生"
    }
    else if (addType.value === 2) {
        return "添加老师"
    }
    else {
        return "未初始化"
    }
})

const delTitle = computed(() => {
    if (delType.value === 1) {
        return "删除学生"
    }
    else if (delType.value === 2) {
        return "删除老师"
    }
    else {
        return "未初始化"
    }
})

let searchKeyword = ref('');
let me = [];
let teachers = [];
let students = [];
let nodes = [];
let links = [];
let results = ref([]);
let isMyTree = true;
let currentTreeUid = "";
let currentTreeName = "";

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
function drawChart() {

    nodes = []
    links = []

    nodes = me.concat(teachers, students)
    let teacherLinks = teachers.map(teacher => {
        return { source: teacher.name, target: me[0].name };
    });
    let studentLinks = students.map(student => {
        return { source: me[0].name, target: student.name }
    });

    links = teacherLinks.concat(studentLinks);

    console.log(nodes, links)

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


    const simulation = d3.forceSimulation(nodes)
        .force('link', d3.forceLink(links).id(d => d.name))
        .force('charge', d3.forceManyBody().strength(-500 * r))
        .force('center', d3.forceCenter(containerWidth / 2, containerHeight / 2));


    // 创建连接线
    const link = svg.selectAll('.link')
        .data(links)
        .enter()
        .append('line')
        .attr('class', 'link')
        .attr('stroke', 'black')
        .attr('stroke-width', 2)
        .attr('marker-end', 'url(#arrow)'); // 使用箭头标记

    // 创建节点
    const node = svg.selectAll('.node')
        .data(nodes)
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
            if (d.type == 0) { return }
            delUid.value = d.uid;
            modifyUid.value = d.uid;
            delName.value = d.name;
            modifyName.value = d.name;
            delToName = currentTreeName;
            modifyToName = currentTreeName;
            delToUid = currentTreeUid;
            modifyToUid = currentTreeUid;
            if (d.type === '1') {
                delType.value = 2
            }
            else {
                delType.value = 1
            }

            modifyRel.value = d.relationships;
            console.log(d.relationships, modifyRel.value)
            event.preventDefault();

            // 显示菜单
            d3.select('.context-menu')
                .style('display', 'block')
                .style('left', event.pageX + 'px')
                .style('top', event.pageY + 'px');


        }).on('dblclick', (event, d) => {
            // 双击事件处理逻辑
            console.log('Double click on node:', d.myPage);
            if (d.myPage && d.myPage.trim() !== '') {
                window.open(d.myPage, '_blank');
            }
        });
    //添加文字
    const texts = svg.selectAll("text")
        .data(nodes)
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

    // 从结果框中获取Name和ID
    const nameElement = selected.querySelector('.top-info p:nth-child(1)');
    const name = nameElement.textContent.split('：')[1];
    const idElement = selected.querySelector('.top-info p:nth-child(2)');
    const id = idElement.textContent.split('：')[1];
    currentTreeName = name
    currentTreeUid = id
    if (id === infoStore.usrInfo.uid) {
        isMyTree = true
    }
    else {
        isMyTree = false
    }


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
    getTree(id)
}

//获取师承树
import { getTreeService } from "@/api/tree"
import { usrInfoStore } from "@/stores/token"
const getTree = async (uid) => {
    console.log(uid)
    let getJson = {
        uid: uid
    }
    let response = await getTreeService(getJson)
    console.log(response.data)
    me = []
    teachers = []
    students = []

    response.data.forEach(obj => {
        if (obj.type === "0") {
            me.push(obj);
        } else if (obj.type === "1") {
            teachers.push(obj);
        } else {
            students.push(obj);
        }
    });
    console.log(me)
    console.log(teachers)
    console.log(students)
    drawChart();
}
const infoStore = usrInfoStore();


import { searchUsersService } from "@/api/user"
import { ElMessage } from 'element-plus';

function getType(id) {
    if (/^\d{9}$/.test(id)) {
        return 1; // uid
    } else if (/^\d{11}$/.test(id)) {
        return 2; // 手机号
    } else {
        return 0; // 姓名
    }
}

const search = async () => {
    if (searchKeyword.value === '') {
        ElMessage.error("请输入值")
        return
    }

    let searchJson = {
        id: searchKeyword.value,
        type: getType(searchKeyword.value)
    }
    let response = await searchUsersService(searchJson);
    console.log(response.data)
    results.value = response.data
    console.log(results.value)
    results.value = results.value.map(item => {
        let usrPic = item.usrPic;
        if (usrPic) {
            usrPic = usrPic.replace(/\\/g, '/').replace(/^/, 'http://localhost:9090');
        } else {
            usrPic = null;
        }
        return {
            ...item,
            usrPic: usrPic
        };
    });
    console.log(results.value)

}

const addStudentOnClick = () => {
    console.log(selectedResult)
    if (selectedResult < 0) {
        ElMessage.error("请选择一个人")
        return;
    }
    // 获取选中的结果框
    const selected = document.querySelector('.result_box:nth-child(' + String(selectedResult) + ')');

    // 从结果框中获取Name和ID
    const nameElement = selected.querySelector('.top-info p:nth-child(1)');
    const name = nameElement.textContent.split('：')[1];
    const idElement = selected.querySelector('.top-info p:nth-child(2)');
    const id = idElement.textContent.split('：')[1];
    addType.value = 1
    addName.value = name
    addUid.value = id
    addToName = currentTreeName
    addToUid = currentTreeUid

    addTableVisible.value = true;

}

const addTeachertOnClick = () => {
    console.log(selectedResult)
    if (selectedResult < 0) {
        ElMessage.error("请选择一个人")
        return;
    }
    // 获取选中的结果框
    const selected = document.querySelector('.result_box:nth-child(' + String(selectedResult) + ')');

    // 从结果框中获取Name和ID
    const nameElement = selected.querySelector('.top-info p:nth-child(1)');
    const name = nameElement.textContent.split('：')[1];
    const idElement = selected.querySelector('.top-info p:nth-child(2)');
    const id = idElement.textContent.split('：')[1];
    addType.value = 2
    addName.value = name
    addUid.value = id
    addToName = currentTreeName
    addToUid = currentTreeUid

    addTableVisible.value = true;

}

const modifyOnClicked = () => {
    modifyTableVisible.value = true
}

const delOnClicked = () => {
    delTableVisible.value = true
}

const editOnClicked = (level) => {
    modifyFrom.level = level
    inModifyTableVisible.value = true
}

import {
    addMyStudentService, addMyTeacherService,
    addOthersStudentService, addOthersTeacherService,
    delMyStudentService, delMyTeacherService,
    delOthersStudentService, delOthersTeacherService,
    modifyMyTreeService, modifyOthersTreeService
} from "@/api/process"

const add = async () => {
    let Level = addFrom.level
    let StartTime = addFrom.startTime
    let EndTime = addFrom.endTime
    console.log(Level === '')

    if (Level === '') {
        ElMessage.error('请选择一个师生关系层次')
        return
    }
    if (StartTime === '') {
        ElMessage.error('请选择师生关系开始时间')
        return
    }
    if (EndTime === '') {
        ElMessage.error('请选择师生关系结束时间')
        return
    }


    addFrom.level = ''
    addFrom.startTime = ''
    addFrom.endTime = ''

    if (isMyTree) {
        let postJson = {
            addUid: addUid.value,
            level: Level,
            startTime: StartTime,
            endTime: EndTime
        }
        if (addType.value === 1) {
            //添加自己的学生
            let response = await addMyStudentService(postJson)
        }
        else {
            //添加自己的老师
            let response = await addMyTeacherService(postJson)
        }
    }
    else {
        let postJson = {
            who: currentTreeUid,
            addUid: addUid.value,
            level: Level,
            startTime: StartTime,
            endTime: EndTime
        }
        if (addType.value === 1) {
            //添加他人的学生
            let response = await addOthersStudentService(postJson)
        }
        else {
            //添加他人的老师
            let response = await addOthersTeacherService(postJson)
        }
    }
    ElMessage.success("已添加申请，等待对方同意")
    addTableVisible.value = false
}

const modify = async () => {
    let Level = modifyFrom.level
    let StartTime = modifyFrom.startTime
    let EndTime = modifyFrom.endTime
    console.log(Level === '')

    if (Level === '') {
        ElMessage.error('请选择一个师生关系层次')
        return
    }
    if (StartTime === '') {
        ElMessage.error('请选择师生关系开始时间')
        return
    }
    if (EndTime === '') {
        ElMessage.error('请选择师生关系结束时间')
        return
    }


    modifyFrom.level = ''
    modifyFrom.startTime = ''
    modifyFrom.endTime = ''

    if (isMyTree) {
        let postJson = {
            modifyUid: modifyUid.value,
            level: Level,
            startTime: StartTime,
            endTime: EndTime
        }
        //修改自己的树
        let response = await modifyMyTreeService(postJson)
    }
    else {
        let postJson = {
            who: currentTreeUid,
            modifyUid: modifyUid.value,
            level: Level,
            startTime: StartTime,
            endTime: EndTime
        }
        //修改他人的树
        let response = await modifyOthersTreeService(postJson)
    }
    ElMessage.success("已添加申请，等待对方同意")
    inModifyTableVisible.value = false
}

const del = async (level) => {
    let Level = level


    if (Level === '') {
        ElMessage.error('请选择一个师生关系层次')
        return
    }

    if (isMyTree) {
        let postJson = {
            delUid: delUid.value,
            level: Level,
        }
        if (delType.value === 1) {
            //删除自己的学生
            let response = await delMyStudentService(postJson)
        }
        else {
            //删除自己的老师
            let response = await delMyTeacherService(postJson)
        }
    }
    else {
        let postJson = {
            who: currentTreeUid,
            delUid: delUid.value,
            level: Level,
        }
        if (delType.value === 1) {
            //删除他人的学生
            let response = await delOthersStudentService(postJson)
        }
        else {
            //删除他人的老师
            let response = await delOthersTeacherService(postJson)
        }
    }
    ElMessage.success("已添加申请，等待对方同意")
}

//删除师生关系
const deleteOnClicked = (level) => {
    ElMessageBox.confirm(
        '你确认删除该师生关系吗？ ',
        '警告',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(() => {

        del(level)
        delTableVisible.value = false
    }).catch(() => {
        //用户点击了取消
        ElMessage({
            type: 'info',
            message: '取消删除',
        })
    })
}

d3.select(document).on('click', () => {
    // 隐藏菜单栏
    d3.select('.context-menu').style('display', 'none');
});

onMounted(() => {
    currentTreeUid = infoStore.usrInfo.uid;
    currentTreeName = infoStore.usrInfo.name;
    getTree(infoStore.usrInfo.uid);
});

</script>

<template>
    <!-- 添加弹窗 -->
    <el-dialog v-model="addTableVisible" aligin-cnter :title="addTitle">
        <el-card>
            <template #header>
                <span v-if="addType === 1">添加{{ addName }}({{ addUid }})为{{ addToName }}({{ addToUid }})的学生</span>
                <span v-else>添加{{ addName }}({{ addUid }})为{{ addToName }}({{ addToUid }})的老师</span>
            </template>
            <el-form :model="addFrom">
                <el-form-item label="师生关系层次">
                    <el-select v-model="addFrom.level" clearable>
                        <el-option v-for="item in addOptions" :key="item.value" :label="item.label"
                            :value="item.value" />
                    </el-select>

                </el-form-item>
                <el-form-item label="师生关系开始时间">
                    <el-date-picker v-model="addFrom.startTime" type="month" format="YYYYMM"
                        @change="handleStartDateChange">

                    </el-date-picker>
                </el-form-item>
                <el-form-item label="师生关系结束时间">
                    <el-date-picker v-model="addFrom.endTime" type="month" format="YYYYMM"
                        @change="handleEndDateChange">

                    </el-date-picker>
                </el-form-item>
            </el-form>
            <el-button @click="add">添加</el-button>
        </el-card>

    </el-dialog>

    <!-- 编辑弹窗 -->
    <el-dialog v-model="modifyTableVisible" aligin-cnter title="编辑">
        <el-card>
            <template #header>
                <span>修改{{ modifyName }}({{ modifyUid }})与{{ modifyToName }}({{ modifyToUid }})的师生关系</span>
            </template>
            <el-table :data="modifyRel" stripe border style="width:100%">
                <el-table-column label="层次">
                    <template #default="scope">
                        <span v-if="scope.row.level === '0'">本科</span>
                        <span v-else-if="scope.row.level === '1'">硕士</span>
                        <span v-else>博士</span>
                    </template>
                </el-table-column>
                <el-table-column prop="startTime" label="开始时间" />
                <el-table-column prop="endTime" label="结束时间" />
                <el-table-column label="修改" align="center" #default="scope">
                    <el-button type="primary" :icon="Edit" circle @click="editOnClicked(scope.row.level)" />
                    <!-- @click="processButtonClick(scope.row.level)" -->
                </el-table-column>
            </el-table>
        </el-card>
    </el-dialog>

    <el-dialog v-model="inModifyTableVisible" aligin-center draggable title="修改起始时间">
        <el-form :model="modifyFrom">
            <el-form-item label="师生关系开始时间">
                <el-date-picker v-model="modifyFrom.startTime" type="month" format="YYYYMM"
                    @change="handleStartDateChangeM">

                </el-date-picker>
            </el-form-item>
            <el-form-item label="师生关系结束时间">
                <el-date-picker v-model="modifyFrom.endTime" type="month" format="YYYYMM"
                    @change="handleEndDateChangeM">

                </el-date-picker>
            </el-form-item>
        </el-form>
        <el-button @click="modify">修改</el-button>
    </el-dialog>

    <!-- 删除弹窗 -->
    <el-dialog v-model="delTableVisible" aligin-cnter :title="delTitle">
        <el-card>
            <template #header>
                <span v-if="delType === 1">删除{{ delName }}({{ delUid }})为{{ delToName }}({{ delToUid }})的学生</span>
                <span v-else>删除{{ delName }}({{ delUid }})为{{ delToName }}({{ delToUid }})的老师</span>
            </template>
            <el-table :data="modifyRel" stripe border style="width:100%">
                <el-table-column label="层次">
                    <template #default="scope">
                        <span v-if="scope.row.level === '0'">本科</span>
                        <span v-else-if="scope.row.level === '1'">硕士</span>
                        <span v-else>博士</span>
                    </template>
                </el-table-column>
                <el-table-column prop="startTime" label="开始时间" />
                <el-table-column prop="endTime" label="结束时间" />
                <el-table-column label="删除" align="center" #default="scope">
                    <el-button type="danger" :icon="Delete" circle @click="deleteOnClicked(scope.row.level)" />
                </el-table-column>
            </el-table>
        </el-card>
    </el-dialog>


    <!-- 右键菜单项 -->
    <div class="context-menu" style="display: none; position: absolute;">
        <div class="context-menu-item" @click="modifyOnClicked">修改</div>
        <div class="context-menu-item" @click="delOnClicked">删除</div>
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
                <el-card v-for="(result, index) in results" :key="result.uid" class="result_box"
                    @click="selectResult(index + 1)" @dblclick="showTree(index + 1)">
                    <div slot="header" class="avatar">
                        <!-- <img :src="result.img" alt="头像"> -->
                        <img v-if="result.usrPic" :src="result.usrPic" alt="头像" />
                        <img v-else :src="avatar" alt="头像" />
                    </div>
                    <div class="info">
                        <div class="top-info">
                            <p>姓名：{{ result.name }}</p>
                            <p>UID：{{ result.uid }}</p>
                        </div>
                        <div class="bottom-info">
                            <p>邮箱：{{ result.email }}</p>
                        </div>
                    </div>
                </el-card>
            </div>
            <div class="button-container">
                <el-button class="add-student" @click="addStudentOnClick">添加为学生</el-button>
                <el-button class="add-teacher" @click="addTeachertOnClick">添加为老师</el-button>
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