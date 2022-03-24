<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-model-item label="检验类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="checkType">
              <j-dict-select-tag type="list" v-model="model.checkType" dictCode="check_type" placeholder="请选择检验类型" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="煤运信息ID" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="trainInfoId">
              <a-input v-model="model.trainInfoId" placeholder="请输入煤运信息ID"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="干燥无灰基挥发分" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="vdaf">
              <a-input-number v-model="model.vdaf" placeholder="请输入干燥无灰基挥发分" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="干燥基全硫" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="std">
              <a-input-number v-model="model.std" placeholder="请输入干燥基全硫" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="收到基低位发热量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="qnetar">
              <a-input-number v-model="model.qnetar" placeholder="请输入收到基低位发热量" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="全水分" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="mt">
              <a-input-number v-model="model.mt" placeholder="请输入全水分" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="内水分" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="mad">
              <a-input-number v-model="model.mad" placeholder="请输入内水分" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="空干基挥发分" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="vad">
              <a-input-number v-model="model.vad" placeholder="请输入空干基挥发分" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="空干基固定碳" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="fcad">
              <a-input-number v-model="model.fcad" placeholder="请输入空干基固定碳" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="空干基硫分" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="stad">
              <a-input-number v-model="model.stad" placeholder="请输入空干基硫分" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="收到基灰分" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="aar">
              <a-input-number v-model="model.aar" placeholder="请输入收到基灰分" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="弹筒热值" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="qbad">
              <a-input-number v-model="model.qbad" placeholder="请输入弹筒热值" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="灰熔点（软化温度）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="st">
              <a-input-number v-model="model.st" placeholder="请输入灰熔点（软化温度）" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="流动温度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="ft">
              <a-input-number v-model="model.ft" placeholder="请输入流动温度" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="半球温度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="ht">
              <a-input-number v-model="model.ht" placeholder="请输入半球温度" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="变形温度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dt">
              <a-input-number v-model="model.dt" placeholder="请输入变形温度" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="空干基灰分" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="aad">
              <a-input-number v-model="model.aad" placeholder="请输入空干基灰分" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="空干基氢含量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="had">
              <a-input-number v-model="model.had" placeholder="请输入空干基氢含量" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="收到基硫含量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="star">
              <a-input-number v-model="model.star" placeholder="请输入收到基硫含量" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="干燥基灰分" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="ad">
              <a-input-number v-model="model.ad" placeholder="请输入干燥基灰分" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="收到基挥发分" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="var">
              <a-input-number v-model="model.var" placeholder="请输入收到基挥发分" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="干燥基挥发分" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="vd">
              <a-input-number v-model="model.vd" placeholder="请输入干燥基挥发分" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="低位发热量MJ" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="qnetarMj">
              <a-input-number v-model="model.qnetarMj" placeholder="请输入低位发热量MJ" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="采样编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cybm">
              <a-input v-model="model.cybm" placeholder="请输入采样编码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="ggrd_mj" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="ggrdMj">
              <a-input-number v-model="model.ggrdMj" placeholder="请输入ggrd_mj" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="ggrad_mj" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="ggradMj">
              <a-input-number v-model="model.ggradMj" placeholder="请输入ggrad_mj" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="ggrar_kcal" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="ggrarKcal">
              <a-input-number v-model="model.ggrarKcal" placeholder="请输入ggrar_kcal" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="煤质颜色" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="color">
              <a-input v-model="model.color" placeholder="请输入煤质颜色"  ></a-input>
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
    name: 'SmartfuelCoalQualityTrainForm',
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
          add: "/coalQualityTrain/smartfuelCoalQualityTrain/add",
          edit: "/coalQualityTrain/smartfuelCoalQualityTrain/edit",
          queryById: "/coalQualityTrain/smartfuelCoalQualityTrain/queryById"
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