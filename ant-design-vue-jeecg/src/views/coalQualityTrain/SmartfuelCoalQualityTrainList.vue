<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="检验类型">
              <j-dict-select-tag placeholder="请选择检验类型" v-model="queryParam.checkType" dictCode="check_type"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="煤运信息ID">
              <a-input placeholder="请输入煤运信息ID" v-model="queryParam.trainInfoId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('煤质数据')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <smartfuel-coal-quality-train-modal ref="modalForm" @ok="modalFormOk"></smartfuel-coal-quality-train-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import SmartfuelCoalQualityTrainModal from './modules/SmartfuelCoalQualityTrainModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: 'SmartfuelCoalQualityTrainList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      SmartfuelCoalQualityTrainModal
    },
    data () {
      return {
        description: '煤质数据管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'检验类型',
            align:"center",
            dataIndex: 'checkType_dictText'
          },
          {
            title:'煤运信息ID',
            align:"center",
            dataIndex: 'trainInfoId'
          },
          {
            title:'干燥无灰基挥发分',
            align:"center",
            dataIndex: 'vdaf'
          },
          {
            title:'干燥基全硫',
            align:"center",
            dataIndex: 'std'
          },
          {
            title:'收到基低位发热量',
            align:"center",
            dataIndex: 'qnetar'
          },
          {
            title:'全水分',
            align:"center",
            dataIndex: 'mt'
          },
          {
            title:'内水分',
            align:"center",
            dataIndex: 'mad'
          },
          {
            title:'空干基挥发分',
            align:"center",
            dataIndex: 'vad'
          },
          {
            title:'空干基固定碳',
            align:"center",
            dataIndex: 'fcad'
          },
          {
            title:'空干基硫分',
            align:"center",
            dataIndex: 'stad'
          },
          {
            title:'收到基灰分',
            align:"center",
            dataIndex: 'aar'
          },
          {
            title:'弹筒热值',
            align:"center",
            dataIndex: 'qbad'
          },
          {
            title:'灰熔点（软化温度）',
            align:"center",
            dataIndex: 'st'
          },
          {
            title:'流动温度',
            align:"center",
            dataIndex: 'ft'
          },
          {
            title:'半球温度',
            align:"center",
            dataIndex: 'ht'
          },
          {
            title:'变形温度',
            align:"center",
            dataIndex: 'dt'
          },
          {
            title:'空干基灰分',
            align:"center",
            dataIndex: 'aad'
          },
          {
            title:'空干基氢含量',
            align:"center",
            dataIndex: 'had'
          },
          {
            title:'收到基硫含量',
            align:"center",
            dataIndex: 'star'
          },
          {
            title:'干燥基灰分',
            align:"center",
            dataIndex: 'ad'
          },
          {
            title:'收到基挥发分',
            align:"center",
            dataIndex: 'var'
          },
          {
            title:'干燥基挥发分',
            align:"center",
            dataIndex: 'vd'
          },
          {
            title:'低位发热量MJ',
            align:"center",
            dataIndex: 'qnetarMj'
          },
          {
            title:'采样编码',
            align:"center",
            dataIndex: 'cybm'
          },
          {
            title:'ggrd_mj',
            align:"center",
            dataIndex: 'ggrdMj'
          },
          {
            title:'ggrad_mj',
            align:"center",
            dataIndex: 'ggradMj'
          },
          {
            title:'ggrar_kcal',
            align:"center",
            dataIndex: 'ggrarKcal'
          },
          {
            title:'煤质颜色',
            align:"center",
            dataIndex: 'color'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/coalQualityTrain/smartfuelCoalQualityTrain/list",
          delete: "/coalQualityTrain/smartfuelCoalQualityTrain/delete",
          deleteBatch: "/coalQualityTrain/smartfuelCoalQualityTrain/deleteBatch",
          exportXlsUrl: "/coalQualityTrain/smartfuelCoalQualityTrain/exportXls",
          importExcelUrl: "coalQualityTrain/smartfuelCoalQualityTrain/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'checkType',text:'检验类型',dictCode:'check_type'})
        fieldList.push({type:'string',value:'trainInfoId',text:'煤运信息ID',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'vdaf',text:'干燥无灰基挥发分',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'std',text:'干燥基全硫',dictCode:''})
        fieldList.push({type:'double',value:'qnetar',text:'收到基低位发热量',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'mt',text:'全水分',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'mad',text:'内水分',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'vad',text:'空干基挥发分',dictCode:''})
        fieldList.push({type:'double',value:'fcad',text:'空干基固定碳',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'stad',text:'空干基硫分',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'aar',text:'收到基灰分',dictCode:''})
        fieldList.push({type:'double',value:'qbad',text:'弹筒热值',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'st',text:'灰熔点（软化温度）',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'ft',text:'流动温度',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'ht',text:'半球温度',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'dt',text:'变形温度',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'aad',text:'空干基灰分',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'had',text:'空干基氢含量',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'star',text:'收到基硫含量',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'ad',text:'干燥基灰分',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'var',text:'收到基挥发分',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'vd',text:'干燥基挥发分',dictCode:''})
        fieldList.push({type:'double',value:'qnetarMj',text:'低位发热量MJ',dictCode:''})
        fieldList.push({type:'string',value:'cybm',text:'采样编码',dictCode:''})
        fieldList.push({type:'double',value:'ggrdMj',text:'ggrd_mj',dictCode:''})
        fieldList.push({type:'double',value:'ggradMj',text:'ggrad_mj',dictCode:''})
        fieldList.push({type:'double',value:'ggrarKcal',text:'ggrar_kcal',dictCode:''})
        fieldList.push({type:'string',value:'color',text:'煤质颜色',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>