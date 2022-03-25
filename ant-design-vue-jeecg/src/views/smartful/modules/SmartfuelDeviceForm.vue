<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24" >
            <a-form-model-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name">
              <a-input v-model="model.name" placeholder="请输入设备名称" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24" >
            <a-form-model-item label="设备类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deviceType">
              <j-category-select v-model="model.deviceType" pcode="B03" placeholder="请选择设备类型"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24" >
            <a-form-model-item label="设备状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deviceStatus">
              <j-dict-select-tag type="list" v-model="model.deviceStatus" dictCode="device_status" placeholder="请选择设备状态" />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="孪生属性" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="smartfuelDeviceTwinTable.loading"
          :columns="smartfuelDeviceTwinTable.columns"
          :dataSource="smartfuelDeviceTwinTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"/>
      </a-tab-pane>
      <a-tab-pane tab="设备属性" :key="refKeys[1]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[1]"
          :loading="smartfuelDeviceAttributeTable.loading"
          :columns="smartfuelDeviceAttributeTable.columns"
          :dataSource="smartfuelDeviceAttributeTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"/>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>

  import { getAction } from '@/api/manage'
  import { FormTypes,getRefPromise,VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
  import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'SmartfuelDeviceForm',
    mixins: [JEditableTableModelMixin],
    components: {
    },
    data() {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        model:{
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
        },
        refKeys: ['smartfuelDeviceTwin', 'smartfuelDeviceAttribute', ],
        tableKeys:['smartfuelDeviceTwin', 'smartfuelDeviceAttribute', ],
        activeKey: 'smartfuelDeviceTwin',
        // 孪生属性
        smartfuelDeviceTwinTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '孪生属性名称',
              key: 'name',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '标记',
              key: 'tag',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '数据源',
              key: 'source',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
          ]
        },
        // 设备属性
        smartfuelDeviceAttributeTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '属性名称',
              key: 'name',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '值',
              key: 'value',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
          ]
        },
        url: {
          add: "/device/smartfuelDevice/add",
          edit: "/device/smartfuelDevice/edit",
          queryById: "/device/smartfuelDevice/queryById",
          smartfuelDeviceTwin: {
            list: '/device/smartfuelDevice/querySmartfuelDeviceTwinByMainId'
          },
          smartfuelDeviceAttribute: {
            list: '/device/smartfuelDevice/querySmartfuelDeviceAttributeByMainId'
          },
        }
      }
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
    },
    methods: {
      addBefore(){
        this.smartfuelDeviceTwinTable.dataSource=[]
        this.smartfuelDeviceAttributeTable.dataSource=[]
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        this.$nextTick(() => {
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.smartfuelDeviceTwin.list, params, this.smartfuelDeviceTwinTable)
          this.requestSubTableData(this.url.smartfuelDeviceAttribute.list, params, this.smartfuelDeviceAttributeTable)
        }
      },
      //校验所有一对一子表表单
      validateSubForm(allValues){
          return new Promise((resolve,reject)=>{
            Promise.all([
            ]).then(() => {
              resolve(allValues)
            }).catch(e => {
              if (e.error === VALIDATE_NO_PASSED) {
                // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
                this.activeKey = e.index == null ? this.activeKey : this.refKeys[e.index]
              } else {
                console.error(e)
              }
            })
          })
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          smartfuelDeviceTwinList: allValues.tablesValue[0].values,
          smartfuelDeviceAttributeList: allValues.tablesValue[1].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     handleCategoryChange(value,backObj){
      this.model = Object.assign(this.model, backObj);
      }

    }
  }
</script>

<style scoped>
</style>