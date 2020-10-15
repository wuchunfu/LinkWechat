import request from '@/utils/request'
const service = config.services.wecom + '/user'

/**
 * 获取离职未分配员工列表
 * @param {*} params 
 * {
 * pageNum:
pageSize:
userName:离职人员名称
beginTime:}
 */
export function getList(params) {
  return request({
    url: service + '/leaveUserNoAllocateList',
    params
  })
}

/**
 * 分配离职员工数据
 * @param {*} data 
{
    "handoverUserid": "原跟进成员的userid",
    "takeoverUserid": "接替成员的userid"
}
 */
export function allocate(data) {
  return request({
    url: service + '/allocateLeaveUserAboutData',
    method: 'put',
    data
  })
}