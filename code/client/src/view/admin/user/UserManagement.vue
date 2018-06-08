<template>
  <div style="width: 80%; margin: auto;">
    <div>
      <el-input v-model="keyword" placeholder="请输入搜索条件" size="small" style="width: 400px;">
        <el-button slot="append" icon="el-icon-search" @click="doSearch"></el-button>
      </el-input>
      <el-button type="primary" size="small" @click="addUserClick">新建用户</el-button>
    </div>
    <div style="margin-top: 20px;">
      <el-table
        :data="pagination.data"
        stripe border
        size="small"
        style="width: 100%;">
        <el-table-column
          prop="username"
          label="用户名"
          width="180"
          align="left">
        </el-table-column>
        <el-table-column
          prop="profile.fullname"
          label="姓名"
          width="180"
          align="left">
        </el-table-column>
        <el-table-column
          prop="profile.gender"
          :formatter="formatterGender"
          label="性别"
          align="left">
        </el-table-column>
        <el-table-column
          prop="profile.idNo"
          label="身份证号"
          align="left">
        </el-table-column>
        <el-table-column
          prop="profile.mobile"
          label="手机号"
          align="left">
        </el-table-column>
        <el-table-column
          label="操作"
          align="center">
          <template slot-scope="scope">
            <el-button @click="viewUserClick(scope.row.id)" type="text" size="small">查看</el-button>
            <el-button @click="editUserClick(scope.row.id)" type="text" size="small">编辑</el-button>
            <el-button @click="deleteUserClick(scope.row.id)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="margin-top: 20px;">
      <el-pagination
        @size-change="changePageSize"
        @current-change="changePageNumber"
        :current-page="pagination.pageNumber"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pagination.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total">
      </el-pagination>
    </div>
    <!-- dialog -->
    <!-- 用户新建 dialog -->
    <el-dialog title="用户编辑" :visible.sync="dialogUserAddVisible" @close="doCloseUserAdd">
      <el-form :model="form" size="small" label-width="100px" @submit.native.prevent>
        <el-form-item label="用户名">
          <el-input v-model="form.username" auto-complete="off" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="form.password" auto-complete="off" placeholder="密码不少于6位"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.profile.fullname" auto-complete="off" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.profile.gender" placeholder="性别">
            <el-option label="未知" value="0"></el-option>
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="form.profile.idNo" auto-complete="off" placeholder="身份证号"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.profile.mobile" auto-complete="off" placeholder="手机号"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUserAddVisible = false">取 消</el-button>
        <el-button type="primary" @click="doAddUser">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 用户查看 dialog -->
    <el-dialog title="用户查看" :visible.sync="dialogUserViewVisible" @close="doCloseUserView">
      <el-form :model="form" size="small" label-width="100px" @submit.native.prevent>
        <el-form-item label="用户名">
          <span>{{form.username}}</span>
        </el-form-item>
        <el-form-item label="姓名">
          <span>{{form.profile.fullname}}</span>
        </el-form-item>
        <el-form-item label="性别">
          <span>{{convertGender(form.profile.gender)}}</span>
        </el-form-item>
        <el-form-item label="身份证号">
          <span>{{form.profile.idNo}}</span>
        </el-form-item>
        <el-form-item label="手机号">
          <span>{{form.profile.mobile}}</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogUserViewVisible = false">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 用户编辑 dialog -->
    <el-dialog title="用户编辑" :visible.sync="dialogUserEditVisible" @close="doCloseUserEdit">
      <el-form :model="form" size="small" label-width="100px" @submit.native.prevent>
        <el-form-item label="用户名">
          <span>{{form.username}}</span>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.profile.fullname" auto-complete="off" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.profile.gender" placeholder="性别">
            <el-option label="未知" value="0"></el-option>
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="form.profile.idNo" auto-complete="off" placeholder="身份证号"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.profile.mobile" auto-complete="off" placeholder="手机号"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUserEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="doUpdateUser">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'user-management',
    data() {
      return {
        keyword: '',
        pagination: {
          pageNumber: 1,
          pageSize: 10,
          total: 0,
          data: []
        },
        sort: {
          sort: '',
          desc: '',
        },
        dialogUserAddVisible: false,
        dialogUserViewVisible: false,
        dialogUserEditVisible: false,
        form: {
          id: null,
          username: '',
          password: '',
          profile: {
            fullname: '',
            gender: 0,
            idNo: '',
            mobile: ''
          }
        }
      }
    },
    created() {
      this.doSearch();
    },
    methods: {
      // 搜索
      doSearch() {
        let self = this;
        this.$api.searchUser({'keyword': self.keyword}, self.pagination, self.sort, function (response) {
          self.pagination.total = response.total;
          self.pagination.data = response.data;
        });
      },
      // 每页记录条数变更
      changePageSize(val) {
        this.pagination.pageSize = val;
        this.doSearch();
      },
      // 页码变更
      changePageNumber(val) {
        this.pagination.pageNumber = val;
        this.doSearch();
      },
      defaultUserForm() {
        this.form.id = null;
        this.form.username = '';
        this.form.password = '';
        this.form.profile.fullname = '';
        this.form.profile.gender = 0;
        this.form.profile.idNo = '';
        this.form.profile.mobile = '';
      },
      // 用户新建点击
      addUserClick() {
        this.defaultUserForm();
        this.dialogUserAddVisible = true;
      },
      // 用户查看点击
      viewUserClick(id) {
        this.defaultUserForm();
        let self = this;
        this.$api.selectUserById(id, function (response) {
          if (response) {
            self.form.username = response.username;
            self.form.profile.fullname = response.profile.fullname;
            self.form.profile.gender = response.profile.gender;
            self.form.profile.idNo = response.profile.idNo;
            self.form.profile.mobile = response.profile.mobile;
            self.dialogUserViewVisible = true;
          } else {
            self.$message.info('用户不存在');
          }
        });
      },
      // 用户编辑点击
      editUserClick(id) {
        this.defaultUserForm();
        let self = this;
        this.$api.selectUserById(id, function (response) {
          if (response) {
            self.form.id = response.id;
            self.form.username = response.username;
            self.form.profile.fullname = response.profile.fullname;
            self.form.profile.gender = response.profile.gender;
            self.form.profile.idNo = response.profile.idNo;
            self.form.profile.mobile = response.profile.mobile;
            self.dialogUserEditVisible = true;
          } else {
            self.$message.info('用户不存在');
          }
        });
      },
      // 用户删除点击
      deleteUserClick(id) {
        let self = this;
        this.$confirm('确认删除该用户？', '确认', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          showClose: false,
          closeOnClickModal: false
        }).then(() => {
          // 确认删除
          this.$api.deleteUserById({id: id}, function (response) {
            self.doSearch();
          }, null, "删除成功", "删除失败");
        }).catch(() => {});
      },
      doCloseUserAdd() {
        this.defaultUserForm();
      },
      doCloseUserView() {
        this.defaultUserForm();
      },
      doCloseUserEdit() {
        this.defaultUserForm();
      },
      // 新建用户
      doAddUser() {
        let self = this;
        if (this.form.username && this.form.password && this.form.password.length >= 6) {
          this.$api.insertUser(this.form, function (response) {
            self.doSearch();
            self.dialogUserAddVisible = false;
          }, null, "新建成功", "responseErrorMessage");
        } else {
          this.$message.error("请输入合法的用户名和密码");
        }
      },
      // 更新用户
      doUpdateUser() {
        let self = this;
        this.$api.updateUserById(self.form, function (response) {
          self.doSearch();
          self.dialogUserEditVisible = false;
        }, null, "更新成功", "更新失败");
      },
      // 性别转换
      formatterGender(row, column) {
        return this.convertGender(row.profile.gender);
      },
      convertGender(val) {
        return val == 1 ? '男' : (val == 2 ? '女' : '未知');
      }
    }
  }
</script>

<style scoped>
</style>
