import { useEffect, useState } from 'react';
import './App.css';
import { deleteStudent, getAllStudents } from "./servers/student";
import {
    Avatar,
    Badge,
    Breadcrumb,
    Button,
    Divider,
    Empty, Image,
    Layout,
    Menu,
    Popconfirm,
    Radio,
    Spin,
    Table,
    Tag
} from 'antd';
import {
    DesktopOutlined,
    FileOutlined,
    LoadingOutlined,
    PieChartOutlined,
    PlusOutlined,
    TeamOutlined,
    UserOutlined,
} from '@ant-design/icons';
import StudentDrawerForm from "./components/StudentDrawerForm";
import { errorNotification, sucessNotification } from "./components/Notification";

const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;

const MyAvatar = ({ name }) => {
    if (name && name.trim().length === 0) {
        return <Avatar icon={<UserOutlined/>}/>;
    }

    const nameSplit = name.trim().split(" ");

    if (nameSplit.length === 1) {
        return <Avatar>{name.charAt(0)}</Avatar>;
    }

    const lastName = nameSplit[nameSplit.length - 1];

    return <Avatar>{`${name.charAt(0)}${lastName.charAt(0)}`}</Avatar>;
}

const removeStudent = (id, callBack) => {
    deleteStudent(id)
        .then(_ => {
            sucessNotification('Student deleted!')
            callBack();
        });
}

const columns = fetchStudents => [
    {
        title: '',
        dataIndex: 'avatar',
        key: 'avatar',
        render: (text, student) => <MyAvatar name={student.name}/>
    },
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'Gender',
        dataIndex: 'gender',
        key: 'gender',
    },
    {
        title: 'Email',
        dataIndex: 'email',
        key: 'email',
    },
    {
        title: 'Actions',
        dataIndex: 'actions',
        key: 'actions',
        render: (text, student) => (
            <Radio.Group>
                <Popconfirm
                    title={`Are you sure you wanna remove the student ${student.name} ?`}
                    onConfirm={() => removeStudent(student.id, fetchStudents)}
                    okText="Yes"
                    cancelText="No"
                >
                    <Radio.Button value="large">Delete</Radio.Button>
                </Popconfirm>
                <Radio.Button style={{ marginLeft: '10px' }} onClick={() => alert('not yet implemented!')}
                              value="default">Edit</Radio.Button>
            </Radio.Group>
        )
    },
];

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin/>;

function App() {
    const [students, setStudents] = useState([]);
    const [collapsed, setCollapsed] = useState(false);
    const [fetching, setFetching] = useState(true);
    const [showDrawer, setShowDrawer] = useState(false);

    useEffect(() => {
        fetchStudents()
    }, []);

    const fetchStudents = () => {
        getAllStudents()
            .then(resp => {
                setStudents(resp.data);
            })
            .catch(err => {
                errorNotification('Error fetching students', err.response.data.message);
            })
            .finally(_ => {
                setFetching(false);
            });
    }

    const formAdd = () => {
        return (
            <>
                <Button type="primary" shape="round"
                        icon={<PlusOutlined/>} size="small"
                        onClick={() => setShowDrawer(!showDrawer)}
                >
                    Add New Student
                </Button>
                <StudentDrawerForm
                    showDrawer={showDrawer}
                    setShowDrawer={setShowDrawer}
                    fetchStudents={fetchStudents}
                />
            </>
        );
    }

    const renderStudents = () => {
        if (fetching) {
            return <Spin indicator={antIcon}/>;
        }

        if (students && students.length === 0) {
            return (
                <>
                    {formAdd()}
                    <Empty/>
                </>
            );
        }

        return (
            <>
                {formAdd()}
                <Table
                    rowKey={(student) => student.id}
                    dataSource={students}
                    columns={columns(fetchStudents)}
                    bordered
                    title={() => (
                        <>
                            <div className="tag-total-students">
                                <Tag>Number of students:</Tag>
                                <Badge overflowCount={9999} count={students.length} className="site-badge-count-4"/>
                            </div>
                        </>
                    )}
                    pagination={{ defaultPageSize: 30, showSizeChanger: true, pageSizeOptions: ['10', '20', '30'] }}
                    scroll={{ y: 350 }}
                />
            </>
        );
    }

    return <Layout style={{ minHeight: '100vh' }}>
        <Sider collapsible collapsed={collapsed} onCollapse={setCollapsed}>
            <div className="logo"/>
            <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
                <Menu.Item key="1" icon={<PieChartOutlined/>}>
                    Option 1
                </Menu.Item>
                <Menu.Item key="2" icon={<DesktopOutlined/>}>
                    Option 2
                </Menu.Item>
                <SubMenu key="sub1" icon={<UserOutlined/>} title="User">
                    <Menu.Item key="3">Tom</Menu.Item>
                    <Menu.Item key="4">Bill</Menu.Item>
                    <Menu.Item key="5">Alex</Menu.Item>
                </SubMenu>
                <SubMenu key="sub2" icon={<TeamOutlined/>} title="Team">
                    <Menu.Item key="6">Team 1</Menu.Item>
                    <Menu.Item key="8">Team 2</Menu.Item>
                </SubMenu>
                <Menu.Item key="9" icon={<FileOutlined/>}>
                    Files
                </Menu.Item>
            </Menu>
        </Sider>
        <Layout className="site-layout">
            <Header className="site-layout-background" style={{ padding: 0 }}/>
            <Content style={{ margin: '0 16px' }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>User</Breadcrumb.Item>
                    <Breadcrumb.Item>Bill</Breadcrumb.Item>
                </Breadcrumb>
                <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                    {renderStudents()}
                </div>
            </Content>
            <Footer style={{ textAlign: 'center' }}>
                <Image
                    width={75}
                    src="https://user-images.githubusercontent.com/40702606/110871298-0ab98d00-82c6-11eb-88e8-20c4d5c9ded5.png"
                />
                <Divider>
                    <a
                        rel="noopener noreferrer"
                        target="_blank"
                        href="https://amigoscode.com/p/full-stack-spring-boot-react">
                        Click here to access Fullstack Spring Boot & React for professionals 11111
                    </a>
                </Divider>
            </Footer>
        </Layout>
    </Layout>;
}

export default App;
