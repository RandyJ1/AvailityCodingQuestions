import React, {useMemo} from "react";
import { useTable } from 'react-table';
import './table.css'

const UserTable = (props) => {
    console.log(props)

    const columns = useMemo(() => [
        { Header: 'Name',
          accessor: 'name'
        },
        { Header: 'NPI Number',
          accessor: 'npiNumber'
        },
        { Header: 'Address',
          accessor: 'address'
        },
        { Header: 'Telephone Number',
          accessor: 'number'
        },
        { Header: 'Email',
          accessor: 'email'
        }
    ], [])
    
    const table = useTable({
        columns: columns,
        data: props.users
    })

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow,
    } = table
    const renderUserTable = () => {
        return (
            <table {...getTableProps()}>
                <thead>
                    {
                        headerGroups.map((headerGroup) => (
                            <tr {...headerGroup.getHeaderGroupProps()}>
                                {
                                    headerGroup.headers.map((column) => (
                                        <th {...column.getHeaderProps()}>
                                            {column.render('Header')}
                                        </th>
                                    ))
                                }
                            </tr>
                        ))
                    }
                </thead>
                <tbody {...getTableBodyProps()}>
                    {
                        rows.map((row) => {
                            prepareRow(row)
                            return (
                                <tr {...row.getRowProps()}>
                                    {
                                        row.cells.map((cell) => {
                                            return <td {...cell.getCellProps}>{cell.render('Cell')}</td>
                                        })
                                    }
                                </tr>
                            )
                        })
                    }
                </tbody>
            </table>
        )
    }
    return renderUserTable()
};

export default UserTable;