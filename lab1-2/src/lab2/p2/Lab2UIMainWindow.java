package lab2.p2;

import lab2.p1.DBType;
import lab2.sql.MyQuery;
import lab2.sql.SQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.ArrayList;
//import static java.lang.System.out;

public class Lab2UIMainWindow extends JFrame {
	private JPanel pnl_Main;
	private MyTable table;
	private JComboBox<DBType> cmbox_DBSystem;
	private JTextField tbox_Team_Name;
	private JTextField tbox_Player_Name;
	private JTextField tbox_Player_Surname;
	private JTextField tbox_Player_Patronymic;
	private JComboBox<Object> cmbox_Action;
	private JButton btn_Action_Do;
	private JButton btn_DBSystem_Connect;
	private JLabel lbl_ConnectionStatus;
	private JLabel lbl_Team;
	private JLabel lbl_Player;
	private JLabel lbl_Team_Name;
	private JLabel lbl_Player_Surname;
	private JLabel lbl_Player_Name;
	private JLabel lbl_Player_Patronymic;
	private JLabel lbl_Action;

	private SQL sql;

	private final String
		ACTION_SHOW_ALL_TEAMS = "Показать все команды",
		ACTION_SHOW_ALL_PLAYERS = "Показать всех игроков",
		ACTION_SHOW_PLAYERS_WHERE_TEAM = "Показать игроков из команды",
		ACTION_ADD_TEAM = "Добавить команду",
		ACTION_ADD_PLAYER = "Добавить игрока",
		ACTION_DELETE_TEAM = "Удалить команду",
		ACTION_DELETE_PLAYER = "Удалить игрока",
		CONNECTION_STATUS_DISCONNECTED = "Disconnected",
		CONNECTION_STATUS_CONNECTED = "Connected";

	private final ArrayList<String> actions = new ArrayList<>() {{
		add(ACTION_SHOW_ALL_TEAMS);
		add(ACTION_SHOW_ALL_PLAYERS);
		add(ACTION_SHOW_PLAYERS_WHERE_TEAM);
		add(ACTION_ADD_TEAM);
		add(ACTION_ADD_PLAYER);
		add(ACTION_DELETE_TEAM);
		add(ACTION_DELETE_PLAYER);
	}};

	public Lab2UIMainWindow() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setContentPane(pnl_Main);

		cmbox_DBSystem.setModel(new DefaultComboBoxModel<>(DBType.values()));
		cmbox_Action.setModel(new DefaultComboBoxModel<>(actions.toArray()));
		lbl_ConnectionStatus.setText(CONNECTION_STATUS_DISCONNECTED);

		setSelectionComponentsEnabled(false);

		btn_DBSystem_Connect.addActionListener(e -> {
			var dbType = DBType.valueOf(cmbox_DBSystem.getSelectedItem().toString());
			String db = "football", u = "le", p = "ledb";
			try {
				sql = new SQL(dbType, db, u, p);

				lbl_ConnectionStatus.setText(CONNECTION_STATUS_CONNECTED);
				setSelectionComponentsEnabled(true);
				cmbox_Action.actionPerformed(null);
			}
			catch (SQLException ex) {
				lbl_ConnectionStatus.setText(CONNECTION_STATUS_DISCONNECTED);
				showErrorMessage(String.format("Не удалось подключится\n%s", ex.getMessage()));
			}
		});

		cmbox_DBSystem.addItemListener(this::cmbox_DBSystem_StateChanged);

		btn_Action_Do.addActionListener(e -> {
			btn_Action_Do_Click();
		});

		cmbox_Action.addActionListener(e -> {
			switch (cmbox_Action.getSelectedItem().toString()) {
				case "" -> {
					setTeamComponentsEnabled(false);
					setPlayerComponentsEnabled(false);
					cmbox_Action.setSelectedIndex(0);
				}
				case ACTION_SHOW_ALL_TEAMS, ACTION_SHOW_ALL_PLAYERS -> {
					setTeamComponentsEnabled(false);
					setPlayerComponentsEnabled(false);
				}
				case ACTION_SHOW_PLAYERS_WHERE_TEAM, ACTION_ADD_TEAM, ACTION_DELETE_TEAM -> {
					setTeamComponentsEnabled(true);
					setPlayerComponentsEnabled(false);
				}
				case ACTION_ADD_PLAYER, ACTION_DELETE_PLAYER -> {
					setTeamComponentsEnabled(true);
					setPlayerComponentsEnabled(true);
				}
			}
		});
	}

	private void setSelectionComponentsEnabled(boolean val) {
		setActionComponentsEnabled(val);
		setTeamComponentsEnabled(val);
		setPlayerComponentsEnabled(val);
	}

	private void setActionComponentsEnabled(boolean val) {
		lbl_Action.setEnabled(val);
		cmbox_Action.setEnabled(val);
		btn_Action_Do.setEnabled(val);
	}

	private void setTeamComponentsEnabled(boolean val) {
		lbl_Team.setEnabled(val);
		lbl_Team_Name.setEnabled(val);
		tbox_Team_Name.setEnabled(val);
	}

	private void setPlayerComponentsEnabled(boolean val) {
		lbl_Player.setEnabled(val);
		lbl_Player_Surname.setEnabled(val);
		tbox_Player_Surname.setEnabled(val);
		lbl_Player_Name.setEnabled(val);
		tbox_Player_Name.setEnabled(val);
		lbl_Player_Patronymic.setEnabled(val);
		tbox_Player_Patronymic.setEnabled(val);
	}

	private void btn_Action_Do_Click() {
		if (lbl_ConnectionStatus.getText().equals(CONNECTION_STATUS_DISCONNECTED)) {
			return;
		}

		try {
			switch (cmbox_Action.getSelectedItem().toString()) {
				case ACTION_SHOW_ALL_TEAMS -> table.setModel(sql.buildTableModel(MyQuery.SELECT_FROM_TEAM));
				case ACTION_SHOW_ALL_PLAYERS -> table.setModel(sql.buildTableModel(MyQuery.SELECT_FROM_PLAYER));
				case ACTION_SHOW_PLAYERS_WHERE_TEAM -> {
					var teamName = tbox_Team_Name.getText();
					if (sql.exists("Team", "Name='%s'", teamName)) {
						table.setModel(sql.buildTableModel(
							MyQuery.SELECT_FROM_PLAYER_WHERE_TEAMID.formatted(
								MyQuery.SELECT_ID_FROM_TEAM_WHERE_NAME.formatted(teamName))));
					}
					else showErrorMessage("Такая команда не существует");
				}
				case ACTION_ADD_TEAM -> {
					String teamName = tbox_Team_Name.getText();
					if (sql.exists("Team", "Name='%s'", teamName)) {
						showErrorMessage("Такая команда уже существует");
						return;
					}
					sql.execute(MyQuery.INSERT_INTO_TEAM, teamName);
				}
				case ACTION_ADD_PLAYER -> {
					String pSrn = tbox_Player_Surname.getText(),
						pName = tbox_Player_Name.getText(),
						pPtr = tbox_Player_Patronymic.getText(),
						teamName = tbox_Team_Name.getText();
					var rs = sql.executeQuery(MyQuery.SELECT_ID_FROM_TEAM_WHERE_NAME, teamName);
					// check team
					if (rs.next()) {
						var teamId = rs.getInt(1);
						// check player
						if (!sql.existsQuery(MyQuery.SELECT_FROM_PLAYER_WHERE, pSrn, pName, pPtr, teamId)) {
							sql.execute(MyQuery.INSERT_INTO_PLAYER, pSrn, pName, pPtr, teamId);
						}
						else showErrorMessage("Такой игрок уже существует");
					}
					else showErrorMessage("Такая команда не существует");
				}
				case ACTION_DELETE_TEAM -> {
					String teamName = tbox_Team_Name.getText();
					// check team
					if (sql.existsQuery(MyQuery.SELECT_ID_FROM_TEAM_WHERE_NAME, teamName)) {
						var rs = sql.executeQuery(MyQuery.SELECT_ID_FROM_TEAM_WHERE_NAME, teamName);
						rs.next();
						var teamId = rs.getInt(1);
						// check players
						if (!sql.existsQuery(MyQuery.SELECT_FROM_PLAYER_WHERE_TEAMID, teamId)) {
							sql.execute(MyQuery.DELETE_FROM_TEAM_WHERE_NAME, teamName);
						}
						else
							showErrorMessage("Невозможно удлить команду: некоторые игроки связаны с ней");
					}
					else showErrorMessage("Такая команда не существует");
				}
				case ACTION_DELETE_PLAYER -> {
					String pSrn = tbox_Player_Surname.getText(),
						pName = tbox_Player_Name.getText(),
						pPtr = tbox_Player_Patronymic.getText(),
						teamName = tbox_Team_Name.getText();
					var rs = sql.executeQuery(MyQuery.SELECT_ID_FROM_TEAM_WHERE_NAME, teamName);
					// check team
					if (rs.next()) {
						var teamId = rs.getInt(1);
						// check player
						if (sql.existsQuery(MyQuery.SELECT_FROM_PLAYER_WHERE, pSrn, pName, pPtr, teamId)) {
							sql.execute(MyQuery.DELETE_FROM_PLAYER_WHERE, pSrn, pName, pPtr, teamId);
						}
						else showErrorMessage("Такой игрок не существует");
					}
					else showErrorMessage("Такая команда не существует");
				}
			}
		}
		catch (Exception e) {
			showErrorMessage(e.getMessage());
		}
	}

	private void cmbox_DBSystem_StateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			try {
				sql.close();
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
			lbl_ConnectionStatus.setText(CONNECTION_STATUS_DISCONNECTED);
			setSelectionComponentsEnabled(false);
		}
	}

	private void showInfoMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
	}

	private void showErrorMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		pnl_Main = new JPanel();
		pnl_Main.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(9, 3, new Insets(0, 5, 0, 0), -1, -1));
		pnl_Main.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		final JLabel label1 = new JLabel();
		label1.setText("DB System");
		panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		lbl_ConnectionStatus = new JLabel();
		lbl_ConnectionStatus.setText("<con. status>");
		panel1.add(lbl_ConnectionStatus, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		cmbox_DBSystem = new JComboBox();
		panel1.add(cmbox_DBSystem, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		lbl_Team = new JLabel();
		lbl_Team.setText("Team");
		panel1.add(lbl_Team, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		lbl_Team_Name = new JLabel();
		lbl_Team_Name.setText("Name");
		panel1.add(lbl_Team_Name, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		tbox_Team_Name = new JTextField();
		panel1.add(tbox_Team_Name, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		lbl_Player = new JLabel();
		lbl_Player.setText("Player");
		panel1.add(lbl_Player, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		lbl_Player_Patronymic = new JLabel();
		lbl_Player_Patronymic.setText("Patronymic");
		panel1.add(lbl_Player_Patronymic, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		tbox_Player_Patronymic = new JTextField();
		panel1.add(tbox_Player_Patronymic, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		lbl_Action = new JLabel();
		lbl_Action.setText("Action");
		panel1.add(lbl_Action, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		cmbox_Action = new JComboBox();
		panel1.add(cmbox_Action, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		btn_Action_Do = new JButton();
		btn_Action_Do.setText("Do");
		panel1.add(btn_Action_Do, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		btn_DBSystem_Connect = new JButton();
		btn_DBSystem_Connect.setText("Connect");
		panel1.add(btn_DBSystem_Connect, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		lbl_Player_Name = new JLabel();
		lbl_Player_Name.setText("Name");
		panel1.add(lbl_Player_Name, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		lbl_Player_Surname = new JLabel();
		lbl_Player_Surname.setText("Surname");
		panel1.add(lbl_Player_Surname, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		tbox_Player_Surname = new JTextField();
		panel1.add(tbox_Player_Surname, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		tbox_Player_Name = new JTextField();
		panel1.add(tbox_Player_Name, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
		pnl_Main.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		final JScrollPane scrollPane1 = new JScrollPane();
		pnl_Main.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		table = new MyTable();
		scrollPane1.setViewportView(table);
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return pnl_Main;
	}

}
