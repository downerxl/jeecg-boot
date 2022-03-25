<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    okText="保存并安排任务"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form-model ref="form" :model="model" :rules="validatorRules">

        <a-form-model-item :labelCol="labelCol"  :wrapperCol="wrapperCol" label="任务类名"  hasFeedback >
<!--          <a-input placeholder="请输入任务类名" v-model="model.jobClassNsame" />-->
          <a-select style="width: 65%" @change="handlePackageChange" >
            <a-select-option v-for="name in packageNames" :key="name" :value="name">{{name}}</a-select-option>
          </a-select>
          <a-select v-model="model.jobClassName" prop="jobClassName" style="width: 35%">
            <a-select-option v-for="d in packageDatas" :key="d.value" :value="d.value">{{d.text}}</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="Cron表达式" prop="cronExpression">
          <!-- <j-cron v-model="model.cronExpression"/>-->
          <j-easy-cron v-model="model.cronExpression" />
        </a-form-model-item>
        <a-form-model-item  :labelCol="labelCol" :wrapperCol="wrapperCol" label="参数" prop="parameter" >
          <a-textarea placeholder="请输入参数" :rows="5" v-model="model.parameter" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol"  :wrapperCol="wrapperCol" label="描述" prop="description">
          <a-textarea placeholder="请输入描述" :rows="3" v-model="model.description" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol"  label="状态" prop="status">
          <j-dict-select-tag type="radioButton" v-model="model.status" dictCode="quartz_status"/>
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  // import JCron from "@/components/jeecg/JCron";
  import cronValidator from "@/components/jeecg/JEasyCron/validator";

  export default {
    name: "QuartzJobModal",
    components: {
      // JCron,
    },
    data () {
      return {
        title:"操作",
        buttonStyle: 'solid',
        visible: false,
        model: {},
        packageDatas: [],
        packageNames: ["org.jiyitech.modules.smartfuel.job","org.jeecg.common.online.api"],
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        cron: {
          label: '',
          value: ''
        },
        confirmLoading: false,
        validatorRules: {
          cronExpression: [
            {required: true, message: '请输入cron表达式!'},
            {validator: cronValidator,}
          ],
          jobClassName: [{required: true, message: '请输入任务类名!'}]
        },
        url: {
          add: "/sys/quartzJob/add",
          edit: "/sys/quartzJob/edit",
          getPackage: "/sys/quartzJob/getPackagePath"
        },
      }
    },
    created () {
      this.initPackageDatas("")
    },
    methods: {
      add() {
        // 统一设置默认值
        this.edit({
          cronExpression: '* * * * * ? *',
          status: 0,
        })
      },
      initPackageDatas(packageName) {
        let packageUrl = this.url.getPackage + "?packageName=" + packageName;
        let method = "get";
        this.packageDatas = [];
        httpAction(packageUrl,"",method).then((res) => {
          if (res.success) {
            const result = res.result
            result.forEach((r) => {
              this.packageDatas.push({
                value: r,
                text: r.substring(r.lastIndexOf(".") + 1,r.length)
              })
            })
          }
        })
      },
      handlePackageChange(value) {
        this.initPackageDatas(value)
      },
      edit (record) {
        this.visible = true;
        this.$nextTick(() => {
          this.$refs.form.resetFields()
          this.model = Object.assign({}, record)
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate((ok, err) => {
          if (ok) {
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

            console.log('提交参数',this.model)
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
                that.close();
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })

          }
        })
      },
      handleCancel () {
        this.close()
      },

    }
  }
</script>

<style scoped>

</style>