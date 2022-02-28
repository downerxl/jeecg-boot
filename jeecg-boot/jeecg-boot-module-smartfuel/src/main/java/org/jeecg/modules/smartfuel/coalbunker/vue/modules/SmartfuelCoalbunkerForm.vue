<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="所属煤仓的编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bunkerId">
              <a-input v-model="model.bunkerId" placeholder="请输入所属煤仓的编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="煤种名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="coalName">
              <a-input v-model="model.coalName" placeholder="请输入煤种名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="煤种的Guid" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="coalId">
              <a-input v-model="model.coalId" placeholder="请输入煤种的Guid"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="煤种密度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="density">
              <a-input-number v-model="model.density" placeholder="请输入煤种密度" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="加仓时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bunkerTime">
              <j-date placeholder="请选择加仓时间" v-model="model.bunkerTime"  style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="煤仓高度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bunkerHeight">
              <a-input-number v-model="model.bunkerHeight" placeholder="请输入煤仓高度" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="煤种体积" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bunkerVolumn">
              <a-input-number v-model="model.bunkerVolumn" placeholder="请输入煤种体积" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="煤层的编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bunkerIndex">
              <a-input-number v-model="model.bunkerIndex" placeholder="请输入煤层的编号" style="width: 100%" />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'SmartfuelCoalbunkerForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/coalbunker/smartfuelCoalbunker/add",
          edit: "/coalbunker/smartfuelCoalbunker/edit",
          queryById: "/coalbunker/smartfuelCoalbunker/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
    }
  }
</script>