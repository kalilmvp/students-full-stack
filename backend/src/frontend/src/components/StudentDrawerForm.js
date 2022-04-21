import { Drawer, Input, Col, Select, Form, Row, Button, Spin } from 'antd';
import { addNewStudent } from "../servers/student";
import { LoadingOutlined } from "@ant-design/icons";
import { useState } from "react";
import { errorNotification, sucessNotification } from "./Notification";

const {Option} = Select;

const antIcon = <LoadingOutlined style={{ fontSize: 24}} spin />

function StudentDrawerForm({showDrawer, setShowDrawer, fetchStudents}) {
    const [form] = Form.useForm();
    const onClose = () => setShowDrawer(false);
    const [submitting, setSubmitting] = useState(false);

    const onFinish = student => {
        setSubmitting(true);
        console.log(JSON.stringify(student, null, 2))
        addNewStudent(student).then(_ => {
            sucessNotification('Student Added', `Student ${student.name} was added`);
            onClose();

            form.setFieldsValue({
                name: null,
                email: null,
                gender: null,
            })

            fetchStudents();
        }).catch(err => {
            console.error(err);
            errorNotification('Error adding the student');
        }).finally(_ => {
            setSubmitting(false);
        });
    };

    const onFinishFailed = errorInfo => {
        // alert(JSON.stringify(errorInfo, null, 2));
        // errorNotification('Error adding the student');
    };

    return <Drawer
        title="Create new student"
        width={720}
        onClose={onClose}
        visible={showDrawer}
        bodyStyle={{paddingBottom: 80}}
        footer={
            <div
                style={{
                    textAlign: 'right',
                }}
            >
                <Button onClick={onClose} style={{marginRight: 8}}>
                    Cancel
                </Button>
            </div>
        }
    >
        <Form layout="vertical"
              onFinishFailed={onFinishFailed}
              onFinish={onFinish}
              form={form}
              hideRequiredMark>
            <Row gutter={16}>
                <Col span={12}>
                    <Form.Item
                        name="name"
                        label="Name"
                        rules={[{required: true, message: 'Please enter student name'}]}
                    >
                        <Input placeholder="Please enter student name"/>
                    </Form.Item>
                </Col>
                <Col span={12}>
                    <Form.Item
                        name="email"
                        label="Email"
                        rules={[{required: true, message: 'Please enter student email'}]}
                    >
                        <Input placeholder="Please enter student email"/>
                    </Form.Item>
                </Col>
            </Row>
            <Row gutter={16}>
                <Col span={12}>
                    <Form.Item
                        name="gender"
                        label="Gender"
                        rules={[{required: true, message: 'Please select a gender'}]}
                    >
                        <Select placeholder="Please select a gender">
                            <Option value="MALE">MALE</Option>
                            <Option value="FEMALE">FEMALE</Option>
                            <Option value="OTHER">OTHER</Option>
                        </Select>
                    </Form.Item>
                </Col>
            </Row>
            <Row>
                <Col span={12}>
                    <Form.Item >
                        <Button type="primary" htmlType="submit">
                            Submit
                        </Button>
                    </Form.Item>
                </Col>
            </Row>
            <Row>
                { submitting ?? <Spin indicator={antIcon} /> }
            </Row>
        </Form>
    </Drawer>
}

export default StudentDrawerForm;
