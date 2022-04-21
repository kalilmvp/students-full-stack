import { useEffect, useState } from 'react';
import './App.css';
import { getAllStudents } from "./servers/student";
import { Layout, Menu, Breadcrumb, Table, Spin, Empty, Button, Badge, Tag, Avatar } from 'antd';
import {
    DesktopOutlined,
    PieChartOutlined,
    FileOutlined,
    TeamOutlined,
    UserOutlined,
    LoadingOutlined, PlusOutlined,
} from '@ant-design/icons';
import StudentDrawerForm from "./components/StudentDrawerForm";

const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;

const MyAvatar = ({ name }) => {
    if (name && name.trim().length === 0) {
        return <Avatar icon={<UserOutlined />} />;
    }

    const nameSplit = name.trim().split(" ");

    if (nameSplit.length === 1) {
        return <Avatar>{name.charAt(0)}</Avatar>;
    }

    const lastName = nameSplit[nameSplit.length -1];

    return <Avatar>{`${name.charAt(0)}${lastName.charAt(0)}`}</Avatar>;
}

const columns = [
    {
        title: '',
        dataIndex: 'avatar',
        key: 'avatar',
        render: (text, student) => <MyAvatar name={student.name}/>
    },,
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
];

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

function App() {
    const [students, setStudents] = useState([]);
    const [collapsed, setCollapsed] = useState(false);
    const [fetching, setFetching] = useState(true);
    const [showDrawer, setShowDrawer] = useState(false);

    useEffect(() => {
        fetchStudents()
    }, []);

    const fetchStudents = () => {
        getAllStudents().then(resp => {
            setStudents(resp.data);
            setFetching(false);
        });
    }

    const renderStudents = () => {
        if (fetching) {
            return <Spin indicator={antIcon} />;
        }

        if (students && students.length === 0) {
            return <Empty />;
        }

        return (
            <>
            <StudentDrawerForm
                showDrawer={showDrawer}
                setShowDrawer={setShowDrawer}
                fetchStudents={fetchStudents}
            />
            <Table
                    rowKey={(student) => student.id}
                    dataSource={students}
                    columns={columns}
                    bordered
                    title={() => (
                        <>
                            <div className="tag-total-students">
                                <Tag>Number of students:</Tag>
                                <Badge overflowCount={9999} count={students.length} className="site-badge-count-4" />
                            </div>
                            <Button type="primary" shape="round"
                                    icon={<PlusOutlined />} size="small"
                                    onClick={() => setShowDrawer(!showDrawer)}
                            >
                                Add New Student
                            </Button>
                        </>
                    )}
                    pagination={{ defaultPageSize: 30, showSizeChanger: true, pageSizeOptions: ['10', '20', '30']}}
                    scroll={{ y: 350 }}
            />
            </>
        );
    }

    return <Layout style={{ minHeight: '100vh' }}>
        <Sider collapsible collapsed={collapsed} onCollapse={setCollapsed}>
            <div className="logo" />
            <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
                <Menu.Item key="1" icon={<PieChartOutlined />}>
                    Option 1
                </Menu.Item>
                <Menu.Item key="2" icon={<DesktopOutlined />}>
                    Option 2
                </Menu.Item>
                <SubMenu key="sub1" icon={<UserOutlined />} title="User">
                    <Menu.Item key="3">Tom</Menu.Item>
                    <Menu.Item key="4">Bill</Menu.Item>
                    <Menu.Item key="5">Alex</Menu.Item>
                </SubMenu>
                <SubMenu key="sub2" icon={<TeamOutlined />} title="Team">
                    <Menu.Item key="6">Team 1</Menu.Item>
                    <Menu.Item key="8">Team 2</Menu.Item>
                </SubMenu>
                <Menu.Item key="9" icon={<FileOutlined />}>
                    Files
                </Menu.Item>
            </Menu>
        </Sider>
        <Layout className="site-layout">
            <Header className="site-layout-background" style={{ padding: 0 }} />
            <Content style={{ margin: '0 16px' }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>User</Breadcrumb.Item>
                    <Breadcrumb.Item>Bill</Breadcrumb.Item>
                </Breadcrumb>
                <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                    {renderStudents()}
                </div>
            </Content>
            <Footer style={{ textAlign: 'center' }}>Amigoscode Project</Footer>
        </Layout>
    </Layout>;
}

export default App;
